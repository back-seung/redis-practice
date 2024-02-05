package com.redis.board.domain.dto.response

import com.redis.board.domain.Board

// Board 조회 DTO
data class BoardResponse(
    val title: String,
    val content: String,
    val writer: String
) {

    companion object {

        fun from(board: Board) = BoardResponse(
            title = board.title,
            content = board.content,
            writer = board.writer
        )

    }

}
