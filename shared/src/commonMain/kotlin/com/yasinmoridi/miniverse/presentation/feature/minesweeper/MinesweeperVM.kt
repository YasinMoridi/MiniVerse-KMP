package com.yasinmoridi.miniverse.presentation.feature.minesweeper

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.random.Random

data class Cell(
    val row: Int,
    val col: Int,
    val isMine: Boolean = false,
    val isRevealed: Boolean = false,
    val isFlagged: Boolean = false,
    val neighborMines: Int = 0
)

enum class GameStatus {
    IN_PROGRESS, WON, LOST
}

class MinesweeperVM : ViewModel() {
    private val rows = 10
    private val cols = 10
    private val minesCount = 10

    var board by mutableStateOf(Array(rows) { r -> Array(cols) { c -> Cell(r, c) } })
        private set

    var status by mutableStateOf(GameStatus.IN_PROGRESS)
        private set

    init {
        resetGame()
    }

    fun resetGame() {
        val newBoard = Array(rows) { r -> Array(cols) { c -> Cell(r, c) } }
        status = GameStatus.IN_PROGRESS
        
        // Place mines
        var placed = 0
        while (placed < minesCount) {
            val r = Random.nextInt(rows)
            val c = Random.nextInt(cols)
            if (!newBoard[r][c].isMine) {
                newBoard[r][c] = newBoard[r][c].copy(isMine = true)
                placed++
            }
        }

        // Calculate neighbors
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                if (!newBoard[r][c].isMine) {
                    var count = 0
                    for (dr in -1..1) {
                        for (dc in -1..1) {
                            val nr = r + dr
                            val nc = c + dc
                            if (nr in 0 until rows && nc in 0 until cols && newBoard[nr][nc].isMine) {
                                count++
                            }
                        }
                    }
                    newBoard[r][c] = newBoard[r][c].copy(neighborMines = count)
                }
            }
        }
        board = newBoard
    }

    fun onCellClick(r: Int, c: Int) {
        if (status != GameStatus.IN_PROGRESS || board[r][c].isFlagged || board[r][c].isRevealed) return

        val newBoard = Array(rows) { row -> board[row].copyOf() }
        
        if (newBoard[r][c].isMine) {
            revealAllMines(newBoard)
            status = GameStatus.LOST
        } else {
            revealCellRecursive(newBoard, r, c)
            checkWin(newBoard)
        }
        board = newBoard
    }

    fun onCellLongClick(r: Int, c: Int) {
        if (status != GameStatus.IN_PROGRESS || board[r][c].isRevealed) return
        val newBoard = Array(rows) { row -> board[row].copyOf() }
        newBoard[r][c] = newBoard[r][c].copy(isFlagged = !newBoard[r][c].isFlagged)
        board = newBoard
    }

    private fun revealCellRecursive(newBoard: Array<Array<Cell>>, r: Int, c: Int) {
        if (r !in 0 until rows || c !in 0 until cols || newBoard[r][c].isRevealed || newBoard[r][c].isFlagged) return

        newBoard[r][c] = newBoard[r][c].copy(isRevealed = true)

        if (newBoard[r][c].neighborMines == 0) {
            for (dr in -1..1) {
                for (dc in -1..1) {
                    revealCellRecursive(newBoard, r + dr, c + dc)
                }
            }
        }
    }

    private fun revealAllMines(newBoard: Array<Array<Cell>>) {
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                if (newBoard[r][c].isMine) {
                    newBoard[r][c] = newBoard[r][c].copy(isRevealed = true)
                }
            }
        }
    }

    private fun checkWin(newBoard: Array<Array<Cell>>) {
        var revealedCount = 0
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                if (newBoard[r][c].isRevealed) revealedCount++
            }
        }
        if (revealedCount == (rows * cols) - minesCount) {
            status = GameStatus.WON
        }
    }
}
