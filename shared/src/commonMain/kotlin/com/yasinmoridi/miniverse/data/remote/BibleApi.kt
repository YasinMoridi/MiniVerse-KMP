package com.yasinmoridi.miniverse.data.remote

import com.yasinmoridi.miniverse.data.remote.model.BibleVerse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class BibleApi(private val client: HttpClient) {
    suspend fun getRandomVerse(): BibleVerse {
        // مثال از یک API فرضی
        return client.get("https://bible-api.com/john 3:16").body()
    }
    
    companion object {
        fun createHttpClient() = HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                })
            }
        }
    }
}
