//package com.toucan.rtp.core.configuration;
//
//import java.time.Duration;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializationContext;
//import org.springframework.data.redis.serializer.RedisSerializer;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//
//@Configuration
//@EnableCaching
//public class RedisConfig {
//	@Autowired
//	private Environment env;
//
//	@Bean
//	public RedisCacheConfiguration redisCacheConfiguration(ObjectMapper objectMapper) {
//		RedisSerializer<Object> serializer = new GenericJackson2JsonRedisSerializer(objectMapper);
//
//		return RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).serializeValuesWith(
//				RedisSerializationContext.SerializationPair.fromSerializer(new EncodeDecode<>(serializer)));
//	}
//
//	@Bean
//	public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory,
//			RedisCacheConfiguration redisCacheConfiguration) {
//		return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(redisCacheConfiguration).build();
//	}
//
//	@Bean
//	public RedisConnectionFactory redisConnectionFactory() {
//		return new LettuceConnectionFactory(env.getProperty("spring.redis.host"),
//				Integer.parseInt(env.getProperty("spring.redis.port")));
//	}
//
//	@Bean
//	public ObjectMapper objectMapper() {
//		ObjectMapper objectMapper = new ObjectMapper();
//		objectMapper.registerModule(new JavaTimeModule());
//		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//		return objectMapper;
//	}
//
//}
