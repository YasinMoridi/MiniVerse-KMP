package com.yasinmoridi.miniverse.di

import com.yasinmoridi.miniverse.data.remote.MiniVerseApi
import com.yasinmoridi.miniverse.presentation.feature.home.HomeVM
import com.yasinmoridi.miniverse.presentation.feature.splash.SplashVM
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val appModule = module {
    single { MiniVerseApi.createHttpClient() }
    single { MiniVerseApi(get()) }

    viewModelOf(::SplashVM)
    viewModelOf(::HomeVM)
}

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(appModule)
    }

// called by iOS etc
fun initKoin() = initKoin {}
