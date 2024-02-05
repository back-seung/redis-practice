package com.redis.board.global.config

import com.redis.board.domain.Board
import com.redis.board.global.properties.RedisProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
@EnableRedisRepositories
@EnableConfigurationProperties(RedisProperty::class)
class RedisConfig(
    private val redisProperty: RedisProperty
) {

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        return LettuceConnectionFactory(redisProperty.host, redisProperty.port)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, Board> {
        val redisTemplate = RedisTemplate<String, Board>()
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = Jackson2JsonRedisSerializer(Board::class.java)
        redisTemplate.connectionFactory = redisConnectionFactory()

        return redisTemplate
    }

}