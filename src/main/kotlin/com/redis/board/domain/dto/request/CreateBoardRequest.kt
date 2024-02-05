package com.redis.board.domain.dto.request

import com.redis.board.domain.Board

// Board 등록 DTO
data class CreateBoardRequest(
    val title: String,
    val content: String,
    val writer: String,
) {

    fun from() = Board(
        title = title,
        content = content,
        writer = writer
    )

}
