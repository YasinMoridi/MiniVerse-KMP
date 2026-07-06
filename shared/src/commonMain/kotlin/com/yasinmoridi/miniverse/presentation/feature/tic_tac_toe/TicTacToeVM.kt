package com.yasinmoridi.miniverse.presentation.feature.tic_tac_toe

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class Player { X, O }

class TicTacToeVM : ViewModel() {
    private var playerCount = 2

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

    fun setPlayerCount(count: Int) {
        playerCount = count
    }

    fun onCellClick(index: Int) {
        if (board[index].isNotEmpty() || winner != null || isDraw) return
        
        // If it's AI's turn (in 1P mode), don't allow clicks
        if (playerCount == 1 && currentPlayer == Player.O) return

        makeMove(index)
    }

    private fun makeMove(index: Int) {
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
            
            // Trigger AI move if it's 1-player mode and it's now O's turn
            if (playerCount == 1 && currentPlayer == Player.O && winner == null && !isDraw) {
                viewModelScope.launch {
                    delay(600) // Small delay for better UX
                    makeAiMove()
                }
            }
        }
    }

    private fun makeAiMove() {
        val availableIndices = board.indices.filter { board[it].isEmpty() }
        if (availableIndices.isEmpty()) return

        // 1. Try to win
        val winMove = findWinningMove("O")
        if (winMove != -1) {
            makeMove(winMove)
            return
        }

        // 2. Block player
        val blockMove = findWinningMove("X")
        if (blockMove != -1) {
            makeMove(blockMove)
            return
        }

        // 3. Take center if available
        if (board[4].isEmpty()) {
            makeMove(4)
            return
        }

        // 4. Random move
        makeMove(availableIndices.random())
    }

    private fun findWinningMove(player: String): Int {
        val winPatterns = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8),
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8),
            listOf(0, 4, 8), listOf(2, 4, 6)
        )

        for (pattern in winPatterns) {
            val values = pattern.map { board[it] }
            if (values.count { it == player } == 2 && values.count { it == "" } == 1) {
                return pattern[values.indexOf("")]
            }
        }
        return -1
    }

    private fun checkWinner(player: String): Boolean {
        val winPatterns = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8),
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8),
            listOf(0, 4, 8), listOf(2, 4, 6)
        )
        return winPatterns.any { pattern ->
            pattern.all { board[it] == player }
        }
    }

    fun resetGame() {
        board = Array(9) { "" }
        currentPlayer = Player.X
        winner = null
        isDraw = false
    }
}
