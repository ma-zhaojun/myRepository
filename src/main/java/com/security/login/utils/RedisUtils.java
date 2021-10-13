package com.security.login.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {
    // 客户端请求服务器时携带的token参数
    public static final String ACCESS_TOKEN = "TOKEN_GIFI_";
    // 客户端用户刷新token的参数
    public static final String REFRESH_TOKEN = "REFESH_GIFT_";
    // 客户端短信验证码
    public static final String PHONE_VALID_CODE = "PHONE_VALID_CODE_";

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 设计缓存 k-v
     *
     * @param key
     * @param value
     * @param timeout 单位毫秒
     */
    public void setValue (String key, String value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * 将token存储到redis缓存中同时设置失效时间
     *
     * @param userId
     * @param value
     * @param timeout
     */
    public void setToken(Long userId, String value, long timeout) {
        setValue(ACCESS_TOKEN + userId, value, timeout);
    }

    /**
     * 通过key值获取缓存中的value
     *
     * @param key
     * @return
     */
    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取redis中的token
     *
     * @param userId
     * @return
     */
    public String getToken(Long userId) {
        return getValue(ACCESS_TOKEN + userId);
    }

    /**
     * 删除缓存
     *
     * @param key
     */
    public void delete(String key){
        redisTemplate.delete(key);
    }
}
