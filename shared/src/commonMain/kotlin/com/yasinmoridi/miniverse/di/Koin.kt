package com.yasinmoridi.miniverse.di

import com.yasinmoridi.miniverse.data.remote.MiniVerseApi
import com.yasinmoridi.miniverse.presentation.feature.home.HomeVM
import com.yasinmoridi.miniverse.presentation.feature.splash.SplashVM
import com.yasinmoridi.miniverse.presentation.feature.tic_tac_toe.TicTacToeVM
import com.yasinmoridi.miniverse.presentation.feature.dots_and_boxes.DotsAndBoxesVM
import com.yasinmoridi.miniverse.presentation.feature.minesweeper.MinesweeperVM
import com.yasinmoridi.miniverse.presentation.feature.othello.OthelloVM
import com.yasinmoridi.miniverse.presentation.feature.methello.MethelloVM
import com.yasinmoridi.miniverse.presentation.feature.snake_bite.SnakeBiteVM
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val appModule = module {
    single { MiniVerseApi.createHttpClient() }
    single { MiniVerseApi(get()) }

    viewModelOf(::SplashVM)
    viewModelOf(::HomeVM)
    viewModelOf(::TicTacToeVM)
    viewModelOf(::DotsAndBoxesVM)
    viewModelOf(::MinesweeperVM)
    viewModelOf(::OthelloVM)
    viewModelOf(::MethelloVM)
    viewModelOf(::SnakeBiteVM)
}

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(appModule)
    }

// called by iOS etc
fun initKoin() = initKoin {}
