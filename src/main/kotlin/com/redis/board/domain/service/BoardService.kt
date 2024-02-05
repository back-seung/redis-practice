package com.redis.board.domain.service

import com.redis.board.domain.dto.request.CreateBoardRequest
import com.redis.board.domain.dto.response.BoardResponse
import com.redis.board.domain.repository.BoardRedisRepository
import com.redis.board.domain.repository.BoardRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.lang.RuntimeException

@Service
class BoardService(
    private val boardRepository: BoardRepository,
    private val boardRedisRepository: BoardRedisRepository
) {

    fun getRecentBoards(): Set<BoardResponse> {
        return boardRedisRepository.getRecentBoards()
    }

    fun getBoard(boardId: Long): BoardResponse {
        val board = boardRepository.findByIdOrNull(boardId) ?: throw RuntimeException("게시물이 존재하지 않습니다.")
        boardRedisRepository.addRecentBoard(board)

        return BoardResponse.from(board)
    }

    @Transactional
    fun saveBoard(createBoardRequest: CreateBoardRequest): Long {
        val board = boardRepository.save(createBoardRequest.from())

        return board.id!!
    }

}
