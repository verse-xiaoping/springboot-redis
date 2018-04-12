package com.github.springboot.redis.service;

import org.apache.commons.lang.StringUtils;
import org.omg.CORBA.TIMEOUT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaoping
 * @date 2018-04-12 16:07
 */
@Service
public class RedisService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 存放String类型，设置过期时间
     */
    public void setString(String key, String value, Long times) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return;
        }
        setObject(key, value, times);
    }

    /**
     * 存放String类型，不设置过期时间
     */
    public void setString(String key, String value) {
        //不设置过期时间
        setString(key, value, null);
    }

    /**
     * 存放list类型，设置过期时间
     */
    public void setList(String key, List<String> value, Long times) {
        if (StringUtils.isEmpty(key) || value.size() == 0) {
            return;
        }
        setObject(key, value, times);
    }

    /**
     * 存放list类型，不设置过期时间
     */
    public void setList(String key, List<String> value) {
        //不设置过期时间
        setList(key, value, null);
    }

    /**
     * 获取String类型的key
     *
     * @param value
     * @return
     */
    public String getStringKey(String value) {
        return stringRedisTemplate.opsForValue().get(value);
    }

    public void setObject(String key, Object value, Long times) {
        //redis 类型：string，list，set，zset，hash
        if (StringUtils.isEmpty(key) || value == null) {
            return;
        }
        //判断类型，防止Object用不识别类型传递进来
        //存放String类型
        if (value instanceof String) {
            if (times != null) {
                //设置过期时间，TimeUnit.SECONDS指定为秒数单位
                stringRedisTemplate.opsForValue().set(key, (String) value, times, TimeUnit.SECONDS);
            }
            stringRedisTemplate.opsForValue().set(key, (String) value);
            return;
        }
        //存放list类型
        if (value instanceof List) {
            List<String> listValue = (List<String>) value;
            for (String string : listValue) {
                stringRedisTemplate.opsForList().leftPush(key, string);
            }
            return;
        }
    }
}
