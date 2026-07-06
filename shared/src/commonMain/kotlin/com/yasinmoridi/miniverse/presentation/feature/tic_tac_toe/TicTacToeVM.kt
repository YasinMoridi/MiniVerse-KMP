package com.yasinmoridi.miniverse.presentation.feature.tic_tac_toe

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

enum class Player { X, O }

class TicTacToeVM : ViewModel() {
    var board by mutableStateOf(Array(9) { "" })
        private set

    var currentPlayer by mutableStateOf(Player.X)
        private set

    var winner by mutableStateOf<Player?>(null)
        private set

    var isDraw by mutableStateOf(false)
        private set

    var blueScore by mutableStateOf(0)
    var redScore by mutableStateOf(0)

    fun onCellClick(index: Int) {
        if (board[index].isNotEmpty() || winner != null || isDraw) return

        val newBoard = board.copyOf()
        newBoard[index] = currentPlayer.name
        board = newBoard

        if (checkWinner(currentPlayer.name)) {
            winner = currentPlayer
            if (winner == Player.O) blueScore++ else redScore++
        } else if (board.all { it.isNotEmpty() }) {
            isDraw = true
        } else {
            currentPlayer = if (currentPlayer == Player.X) Player.O else Player.X
        }
    }

    fun resetGame() {
        board = Array(9) { "" }
        currentPlayer = Player.X
        winner = null
        isDraw = false
    }

    private fun checkWinner(player: String): Boolean {
        val winPatterns = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8), // Rows
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8), // Cols
            listOf(0, 4, 8), listOf(2, 4, 6)              // Diagonals
        )
        return winPatterns.any { pattern ->
            pattern.all { board[it] == player }
        }
    }
}
