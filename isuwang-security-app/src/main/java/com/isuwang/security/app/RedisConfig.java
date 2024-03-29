package com.isuwang.security.app;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cache.interceptor.KeyGenerator;
import java.lang.reflect.Method;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis配置类
 * @author hou
 * @date   2018/11/6.
 *
 */

@Configuration
public class RedisConfig {
    @Bean
    @SuppressWarnings("all")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();

        template.setConnectionFactory(factory);
        // key采用String的序列化方式
        // template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        // template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        // template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value序列化方式采用jackson
        // template.setHashValueSerializer(jackson2JsonRedisSerializer);

        // key采用string的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用jdkSerialization的序列化方式
        template.setHashKeySerializer(jdkSerializationRedisSerializer);
        // value采用string序列化方式
        //template.setValueSerializer(stringRedisSerializer);
        // hash的value采用jdkSerialization序列化方式
        template.setHashValueSerializer(jdkSerializationRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
}
