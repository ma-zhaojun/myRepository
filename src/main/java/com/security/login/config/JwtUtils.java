package com.security.login.config;

import com.security.login.entity.SecurityUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.Resource;
import java.util.Date;

@Configuration
public class JwtUtils {
    @Resource
    private JwtProperties jwtProperties;

    /**
     * 创建token
     * iss: jwt签发者
     * sub: jwt所面向的用户
     * aud: 接收jwt的一方
     * exp: jwt的过期时间，这个过期时间必须要大于签发时间
     * nbf: 定义在什么时间之前，该jwt都是不可用的.
     * iat: jwt的签发时间
     * jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
     *
     * @param subject 签发用户
     * @return
     */
    public String createToken(String subject) {
        Date nowDate = new Date();
        //过期时间 单位毫秒
        Date expireDate = new Date(nowDate.getTime() + jwtProperties.expire * 1000);
        return Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setSubject(subject)
                .setIssuedAt(nowDate) // jwt的签发时间
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.ES256, jwtProperties.secret) //base64加密 通过加盐的方式
                .compact();
    }

    /**
     * 获取token中注册信息
     *
     * 得到DefaultJwtParser
     * Claims claims = Jwts.parser()
     * 设置签名的秘钥
     * .setSigningKey(JwtConstants.SECRET)
     * 设置需要解析的jwt
     * .parseClaimsJws(token).getBody();
     * @param token
     * @return
     */
    public Claims getTokenClaim(String token) {
        try { // 根据密钥获取token中注册信息
            return Jwts.parser().setSigningKey(jwtProperties.secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 验证token
     *
     * @param token
     * @param userDetails
     * @return
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        SecurityUser user = (SecurityUser) userDetails;
        String username = getUsernameFromToken(token);
        return (StringUtils.equals(username, user.getUsername()) && !isTokenExpired(token));
    }

    /**
     * 从token中获取注册信息 （与getTokenClaim方法重复）
     *
     * @param token
     * @return
     */
    public Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(jwtProperties.secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 判断token是否过期
     *
     * @param token
     * @return
     */
    public Boolean isTokenExpired(String token) {
        try {
            Claims claims = getTokenClaim(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 验证token是否过期
     *
     * @param expirationTime
     * @return
     */
    public boolean isTokenExpired (Date expirationTime) {
        return expirationTime.before(new Date());
    }

    /**
     * 获取token的失效时间
     *
     * @param token
     * @return
     */
    public Date getExpirationDateFromToken(String token) {
        return getTokenClaim(token).getExpiration();
    }

    /**
     * 从token获取用户名
     *
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token) {
        return getTokenClaim(token).getSubject();
    }

    /**
     * 获取jwt（token）发布时间
     *
     * @param token
     * @return
     */
    public Date getIssuedAtDateFromToken(String token) {
        return getTokenClaim(token).getIssuedAt();
    }
}
