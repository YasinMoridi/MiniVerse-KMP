package com.yasinmoridi.miniverse.presentation.feature.methello

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasinmoridi.miniverse.presentation.feature.othello.OthelloPiece
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MethelloVM : ViewModel() {
    private var playerCount = 2
    private val size = 6

    var board by mutableStateOf(Array(size) { Array(size) { OthelloPiece.NONE } })
        private set

    var currentPlayer by mutableStateOf(OthelloPiece.BLACK)
        private set

    var blackScore by mutableStateOf(2)
        private set
    var whiteScore by mutableStateOf(2)
        private set

    var winner by mutableStateOf<OthelloPiece?>(null)
        private set

    var isDraw by mutableStateOf(false)
        private set

    var gameOver by mutableStateOf(false)
        private set

    var validMoves by mutableStateOf(listOf<Pair<Int, Int>>())
        private set

    init {
        resetGame()
    }

    fun setPlayerCount(count: Int) {
        playerCount = count
    }

    fun resetGame() {
        board = Array(size) { Array(size) { OthelloPiece.NONE } }
        // Center for 6x6 is indices 2 and 3
        board[2][2] = OthelloPiece.WHITE
        board[2][3] = OthelloPiece.BLACK
        board[3][2] = OthelloPiece.BLACK
        board[3][3] = OthelloPiece.WHITE
        
        currentPlayer = OthelloPiece.BLACK
        winner = null
        isDraw = false
        gameOver = false
        updateValidMoves()
        updateScores()
    }

    fun onCellClick(row: Int, col: Int) {
        if (gameOver || board[row][col] != OthelloPiece.NONE) return
        if (playerCount == 1 && currentPlayer == OthelloPiece.WHITE) return

        if (isValidMove(row, col, currentPlayer)) {
            makeMove(row, col, currentPlayer)
            checkTurn()
        }
    }

    private fun makeMove(row: Int, col: Int, piece: OthelloPiece) {
        val newBoard = board.map { it.copyOf() }.toTypedArray()
        newBoard[row][col] = piece
        
        val directions = listOf(
            -1 to -1, -1 to 0, -1 to 1,
            0 to -1,           0 to 1,
            1 to -1,  1 to 0,  1 to 1
        )

        for ((dr, dc) in directions) {
            if (canCapture(row, col, dr, dc, piece)) {
                var r = row + dr
                var c = col + dc
                while (newBoard[r][c] != piece) {
                    newBoard[r][c] = piece
                    r += dr
                    c += dc
                }
            }
        }
        
        board = newBoard
        updateScores()
    }

    private fun isValidMove(row: Int, col: Int, piece: OthelloPiece): Boolean {
        if (board[row][col] != OthelloPiece.NONE) return false
        
        val directions = listOf(
            -1 to -1, -1 to 0, -1 to 1,
            0 to -1,           0 to 1,
            1 to -1,  1 to 0,  1 to 1
        )

        for ((dr, dc) in directions) {
            if (canCapture(row, col, dr, dc, piece)) return true
        }
        return false
    }

    private fun canCapture(row: Int, col: Int, dr: Int, dc: Int, piece: OthelloPiece): Boolean {
        val opponent = if (piece == OthelloPiece.BLACK) OthelloPiece.WHITE else OthelloPiece.BLACK
        var r = row + dr
        var c = col + dc
        
        if (r !in 0 until size || c !in 0 until size || board[r][c] != opponent) return false
        
        r += dr
        c += dc
        while (r in 0 until size && c in 0 until size) {
            if (board[r][c] == piece) return true
            if (board[r][c] == OthelloPiece.NONE) return false
            r += dr
            c += dc
        }
        return false
    }

    private fun checkTurn() {
        val nextPlayer = if (currentPlayer == OthelloPiece.BLACK) OthelloPiece.WHITE else OthelloPiece.BLACK
        
        if (hasValidMove(nextPlayer)) {
            currentPlayer = nextPlayer
            updateValidMoves()
            if (playerCount == 1 && currentPlayer == OthelloPiece.WHITE) {
                viewModelScope.launch {
                    delay(600)
                    makeAiMove()
                }
            }
        } else if (!hasValidMove(currentPlayer)) {
            validMoves = emptyList()
            endGame()
        } else {
            updateValidMoves()
            if (playerCount == 1 && currentPlayer == OthelloPiece.WHITE) {
                viewModelScope.launch {
                    delay(600)
                    makeAiMove()
                }
            }
        }
    }

    private fun updateValidMoves() {
        val moves = mutableListOf<Pair<Int, Int>>()
        for (r in 0 until size) {
            for (c in 0 until size) {
                if (isValidMove(r, c, currentPlayer)) {
                    moves.add(r to c)
                }
            }
        }
        validMoves = moves
    }

    private fun hasValidMove(piece: OthelloPiece): Boolean {
        for (r in 0 until size) {
            for (c in 0 until size) {
                if (isValidMove(r, c, piece)) return true
            }
        }
        return false
    }

    private fun makeAiMove() {
        val validMovesList = mutableListOf<Pair<Int, Int>>()
        for (r in 0 until size) {
            for (c in 0 until size) {
                if (isValidMove(r, c, OthelloPiece.WHITE)) {
                    validMovesList.add(r to c)
                }
            }
        }

        if (validMovesList.isNotEmpty()) {
            val bestMove = validMovesList.maxByOrNull { (r, c) ->
                countCapturedPieces(r, c, OthelloPiece.WHITE)
            }!!
            makeMove(bestMove.first, bestMove.second, OthelloPiece.WHITE)
        }
        
        checkTurn()
    }

    private fun countCapturedPieces(row: Int, col: Int, piece: OthelloPiece): Int {
        var count = 0
        val directions = listOf(
            -1 to -1, -1 to 0, -1 to 1,
            0 to -1,           0 to 1,
            1 to -1,  1 to 0,  1 to 1
        )
        for ((dr, dc) in directions) {
            if (canCapture(row, col, dr, dc, piece)) {
                var r = row + dr
                var c = col + dc
                while (board[r][c] != piece) {
                    count++
                    r += dr
                    c += dc
                }
            }
        }
        return count
    }

    private fun updateScores() {
        var b = 0
        var w = 0
        for (r in 0 until size) {
            for (c in 0 until size) {
                if (board[r][c] == OthelloPiece.BLACK) b++
                if (board[r][c] == OthelloPiece.WHITE) w++
            }
        }
        blackScore = b
        whiteScore = w
    }

    private fun endGame() {
        gameOver = true
        if (blackScore > whiteScore) {
            winner = OthelloPiece.BLACK
        } else if (whiteScore > blackScore) {
            winner = OthelloPiece.WHITE
        } else {
            isDraw = true
        }
    }
}
