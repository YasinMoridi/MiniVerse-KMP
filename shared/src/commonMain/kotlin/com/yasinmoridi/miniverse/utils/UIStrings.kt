package com.yasinmoridi.miniverse.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

enum class AppLanguage {
    ENGLISH, PERSIAN
}

object UIStrings {
    var language by mutableStateOf(AppLanguage.ENGLISH)

    private val isEn get() = language == AppLanguage.ENGLISH

    val APP_NAME get() = if (isEn) "MiniVerse" else "مینی‌ورس"
    val MULTI get() = if (isEn) "MULTI" else "چند نفره"
    val PLAYER_GAMES get() = if (isEn) "PLAYER GAMES" else "بازی‌های بازیکن"
    val PLAYER get() = if (isEn) "PLAYER" else "بازیکن"
    val PLAYERS get() = if (isEn) "PLAYERS" else "بازیکنان"
    val SHARE_WITH_FRIENDS get() = if (isEn) "SHARE WITH FRIENDS!" else "با دوستان به اشتراک بگذارید!"
    val BACK get() = if (isEn) "Back" else "برگشت"
    
    // Home
    fun playersGames(count: Int) = if (isEn) "$count PLAYER GAMES" else "$count بازی بازیکن"

    // Game Names
    val TAP_COUNTER get() = if (isEn) "TAP COUNTER" else "تپ کانتر"
    val GUESS_THE_NUMBER get() = if (isEn) "GUESS THE NUMBER" else "حدس عدد"
    val SNAKE_BITE get() = if (isEn) "SNAKE BITE" else "مار بازی"
    val AVOID_THE_BLOCKS get() = if (isEn) "AVOID THE BLOCKS" else "دوری از بلوک‌ها"
    val QUIZ_MANIA get() = if (isEn) "QUIZ MANIA" else "کویز"
    val WORD_SCRAMBLE get() = if (isEn) "WORD SCRAMBLE" else "کلمات درهم"
    val BRICK_BREAKER get() = if (isEn) "BRICK BREAKER" else "آجر شکن"
    val CATCH_THE_OBJECT get() = if (isEn) "CATCH THE OBJECT" else "گرفتن اشیاء"
    val TIC_TAC_TOE get() = if (isEn) "TIC TAC TOE" else "دوز"
    val MINESWEEPER get() = if (isEn) "MINESWEEPER" else "مین‌روب"
    val OTHELLO get() = if (isEn) "OTHELLO" else "اتلو"
    val METHELLO get() = if (isEn) "METHELLO" else "متلو"
    val COMING_SOON get() = if (isEn) "COMING SOON" else "به زودی"

    // Settings
    val SETTINGS get() = if (isEn) "SETTINGS" else "تنظیمات"
    val VOLUME get() = if (isEn) "Volume" else "صدا"
    val RESOLUTION get() = if (isEn) "Resolution" else "رزولوشن"
    val MAX_FPS get() = if (isEn) "Max FPS" else "فریم بر ثانیه"
    val VIBRATION get() = if (isEn) "Vibration" else "لرزش"
    val SOUND get() = if (isEn) "Sound" else "صدا"
    val TERMS_AND_PRIVACY get() = if (isEn) "Our Terms and Privacy Policy." else "شرایط و سیاست حریم خصوصی ما."
    
    // Additional strings for language selection
    val LANGUAGE get() = if (isEn) "Language" else "زبان"
    val ENGLISH get() = "English"
    val PERSIAN get() = "فارسی"
}
