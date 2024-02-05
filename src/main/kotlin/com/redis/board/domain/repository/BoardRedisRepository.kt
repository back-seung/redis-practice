package com.redis.board.domain.repository

import com.redis.board.domain.Board
import com.redis.board.domain.dto.response.BoardResponse
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class BoardRedisRepository(
    private val redisTemplate: RedisTemplate<String, Board>
) {

    private final val zSetOperations = redisTemplate.opsForZSet()

    companion object {
        private const val REDIS_KEY = "BOARD"           // Redis <Key, Value> 중 Key
        private const val READ_BOARD_MIN_COUNT = 0L     // 0 ~ 4건까지 조회
        private const val READ_BOARD_MAX_COUNT = 4L
        private const val SAVE_BOARD_LIMIT_COUNT = -6L  // Redis 에 저장된 마지막 5번째 원소
    }

    // 원소의 개수를 5개로 유지하기 위해 최근 조회한 게시물을 저장함과 동시에 가장 마지막으로 조회했던 원소를 지움
    // Score 는 원소가 저장되는 시간을 기준으로 함
    fun addRecentBoard(board: Board) {
        zSetOperations.add(REDIS_KEY, board, System.currentTimeMillis().toDouble())
        zSetOperations.removeRange(REDIS_KEY, SAVE_BOARD_LIMIT_COUNT, SAVE_BOARD_LIMIT_COUNT)
    }

    fun getRecentBoards(): Set<BoardResponse> {
        val boards = zSetOperations.reverseRange(REDIS_KEY, READ_BOARD_MIN_COUNT, READ_BOARD_MAX_COUNT) ?: emptySet()

        return boards.map { BoardResponse.from(it as Board) }.toSet()
    }

}
