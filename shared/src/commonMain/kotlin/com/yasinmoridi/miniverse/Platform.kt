package com.yasinmoridi.miniverse

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform