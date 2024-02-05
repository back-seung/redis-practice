package com.redis.board.domain

import jakarta.persistence.*
import java.io.Serializable

@Entity
@Table(name = "boards")
class Board (

    @Column(name = "title")
    var title: String,

    @Column(name = "content")
    var content: String,

    @Column(name = "writer")
    var writer: String

): Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

}
