package com.redis.board.domain.controller

import com.redis.board.domain.dto.response.BoardResponse
import com.redis.board.domain.dto.request.CreateBoardRequest
import com.redis.board.domain.service.BoardService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/boards")
class BoardController(
    private val boardService: BoardService
) {

    @PostMapping
    fun saveBoard(@RequestBody createBoardRequest: CreateBoardRequest): ResponseEntity<Unit> {
        val id = boardService.saveBoard(createBoardRequest)

        return ResponseEntity.created(URI.create(String.format("/boards/%d", id))).build()
    }

    @GetMapping("/recent")
    fun getRecentBoards(): ResponseEntity<Set<BoardResponse>> =
        ResponseEntity.ok(boardService.getRecentBoards())

    @GetMapping("/{boardId}")
    fun getBoards(@PathVariable boardId: Long): ResponseEntity<BoardResponse> =
        ResponseEntity.ok(boardService.getBoard(boardId))

}