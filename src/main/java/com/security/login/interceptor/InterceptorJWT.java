package com.security.login.interceptor;

import com.security.login.config.IgnoreSecurity;
import com.security.login.config.JwtProperties;
import com.security.login.config.JwtUtils;
import com.security.login.exception.BusinessException;
import com.security.login.utils.RedisUtils;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class InterceptorJWT implements HandlerInterceptor {

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private JwtUtils jwtUtils;
    @Resource
    private JwtProperties jwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 若目标方法忽略安全性检查 则直接调用目标方法
         *
         * .isAssignableFrom()方法与instanceof关键字的区别总结为以下两个点：
         * isAssignableFrom()方法是从类继承的角度去判断，instanceof关键字是从实例继承的角度去判断。
         * isAssignableFrom()方法是判断是否为某个类的父类，instanceof关键字是判断是否某个类的子类。
         */
        if(handler.getClass().isAssignableFrom(HandlerMethod.class)){
            // 如果方法上有@IgnoreSecurity,则不需要token验证
            IgnoreSecurity ignoreSecurity = ((HandlerMethod) handler).getMethodAnnotation(IgnoreSecurity.class);
            if (ignoreSecurity != null) {
                return true;
            }
        } else {
            String token = request.getParameter(jwtProperties.header);
            if (StringUtils.isNotBlank(token)) {
                Claims claims = jwtUtils.getTokenClaim(token);
                Long userId = (Long)claims.get("userId");
                String redisToken = redisUtils.getToken(userId);
                token = request.getParameter(jwtProperties.header);
                if (!StringUtils.equals(redisToken, token)) {
                    throw new BusinessException(jwtProperties.header + "失效，请重新登录。");
                } else if (claims == null || jwtUtils.isTokenExpired(claims.getExpiration())) {
                    throw new BusinessException(jwtProperties.header + "失效，请重新登录。");
                }
                // 设置 identityId 用户身份ID
                request.setAttribute("identityId", claims.getSubject());
            } else {
                throw new BusinessException(jwtProperties.header + "失效，请重新登录。");
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

}
