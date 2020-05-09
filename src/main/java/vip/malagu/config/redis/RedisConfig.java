package vip.malagu.config.redis;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration.JedisPoolingClientConfigurationBuilder;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@PropertySource("classpath:redis.properties")
public class RedisConfig {
 
    @Value("${redis.host}")
    private String host;
 
    @Value("${redis.password}")
    private String password;
 
    @Value("${redis.port}")
    private Integer port;
    
    @Value("${redis.databaseIndex:0}")
    private int databaseIndex;
    
    @Value("${redis.maxIdle}")
    private Integer maxIdle;
 
    @Value("${redis.maxTotal}")
    private Integer maxTotal;
 
    @Value("${redis.maxWaitMillis}")
    private Integer maxWaitMillis;
 
    @Value("${redis.minEvictableIdleTimeMillis}")
    private Integer minEvictableIdleTimeMillis;
 
    @Value("${redis.numTestsPerEvictionRun}")
    private Integer numTestsPerEvictionRun;
 
    @Value("${redis.timeBetweenEvictionRunsMillis}")
    private long timeBetweenEvictionRunsMillis;
 
    @Value("${redis.testOnBorrow}")
    private boolean testOnBorrow;
 
    @Value("${redis.testWhileIdle}")
    private boolean testWhileIdle;
    
    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(maxIdle);
		jedisPoolConfig.setMaxTotal(maxTotal);
		jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
		jedisPoolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		jedisPoolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
		jedisPoolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		jedisPoolConfig.setTestOnBorrow(testOnBorrow);
		jedisPoolConfig.setTestWhileIdle(testWhileIdle);
        return jedisPoolConfig;
    }

	@Bean
    public JedisConnectionFactory JedisConnectionFactory(JedisPoolConfig poolConfig){
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration ();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        redisStandaloneConfiguration.setDatabase(databaseIndex);
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jedisClientConfiguration = (JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
        jedisClientConfiguration.poolConfig(poolConfig);
        JedisConnectionFactory factory = new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration.build());
        return factory;
    }
    
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(JedisConnectionFactory factory) {
	    RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
	    template.setConnectionFactory(factory);
	    GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
	    template.setKeySerializer(new StringRedisSerializer());
	    template.setValueSerializer(genericJackson2JsonRedisSerializer);
	    template.setHashKeySerializer(genericJackson2JsonRedisSerializer);
	    template.setHashValueSerializer(genericJackson2JsonRedisSerializer);
	    template.afterPropertiesSet();
	    return template;
    }

}