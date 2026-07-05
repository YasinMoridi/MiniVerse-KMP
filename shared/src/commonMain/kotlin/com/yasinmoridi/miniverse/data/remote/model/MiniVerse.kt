package com.yasinmoridi.miniverse.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class MiniVerse(
    val book: String,
    val chapter: Int,
    val verse: Int,
    val text: String
)
