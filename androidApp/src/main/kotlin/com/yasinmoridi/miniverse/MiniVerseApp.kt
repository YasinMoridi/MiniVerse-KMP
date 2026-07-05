package com.yasinmoridi.miniverse

import android.app.Application
import com.yasinmoridi.miniverse.di.initKoin
import org.koin.android.ext.koin.androidContext

class MiniVerseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MiniVerseApp)
        }
    }
}
