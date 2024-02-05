package com.redis.board.global.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.data.redis")
class RedisProperty(
    val host: String,
    val port: Int,
)
