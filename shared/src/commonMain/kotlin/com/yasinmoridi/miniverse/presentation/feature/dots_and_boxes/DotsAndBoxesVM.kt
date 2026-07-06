package com.yasinmoridi.miniverse.presentation.feature.dots_and_boxes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class Player { BLUE, RED }

class DotsAndBoxesVM : ViewModel() {
    private var playerCount = 2
    private val size = 5 // 5x5 squares, 6x6 dots

    var horizontalLines by mutableStateOf(Array(size + 1) { arrayOfNulls<Player>(size) })
        private set
    var verticalLines by mutableStateOf(Array(size) { arrayOfNulls<Player>(size + 1) })
        private set
    var boxes by mutableStateOf(Array(size) { Array<Player?>(size) { null } })
        private set

    var currentPlayer by mutableStateOf(Player.RED)
        private set

    var blueScore by mutableStateOf(0)
    var redScore by mutableStateOf(0)

    var winner by mutableStateOf<Player?>(null)
        private set

    fun setPlayerCount(count: Int) {
        playerCount = count
    }

    fun onHorizontalLineClick(row: Int, col: Int) {
        if (horizontalLines[row][col] != null || winner != null) return
        if (playerCount == 1 && currentPlayer == Player.BLUE) return

        makeHorizontalMove(row, col)
    }

    fun onVerticalLineClick(row: Int, col: Int) {
        if (verticalLines[row][col] != null || winner != null) return
        if (playerCount == 1 && currentPlayer == Player.BLUE) return

        makeVerticalMove(row, col)
    }

    private fun makeHorizontalMove(row: Int, col: Int) {
        val newLines = horizontalLines.map { it.copyOf() }.toTypedArray()
        newLines[row][col] = currentPlayer
        horizontalLines = newLines
        
        val boxesCompleted = checkBoxesCompleted(row, col, isHorizontal = true)
        handleTurnEnd(boxesCompleted)
    }

    private fun makeVerticalMove(row: Int, col: Int) {
        val newLines = verticalLines.map { it.copyOf() }.toTypedArray()
        newLines[row][col] = currentPlayer
        verticalLines = newLines
        
        val boxesCompleted = checkBoxesCompleted(row, col, isHorizontal = false)
        handleTurnEnd(boxesCompleted)
    }

    private fun checkBoxesCompleted(row: Int, col: Int, isHorizontal: Boolean): Boolean {
        var completedCount = 0
        val newBoxes = boxes.map { it.copyOf() }.toTypedArray()
        
        if (isHorizontal) {
            // Check box above
            if (row > 0) {
                if (horizontalLines[row - 1][col] != null && 
                    verticalLines[row - 1][col] != null && 
                    verticalLines[row - 1][col + 1] != null &&
                    horizontalLines[row][col] != null) {
                    if (newBoxes[row - 1][col] == null) {
                        newBoxes[row - 1][col] = currentPlayer
                        completedCount++
                    }
                }
            }
            // Check box below
            if (row < size) {
                if (horizontalLines[row][col] != null && 
                    verticalLines[row][col] != null && 
                    verticalLines[row][col + 1] != null &&
                    horizontalLines[row + 1][col] != null) {
                    if (newBoxes[row][col] == null) {
                        newBoxes[row][col] = currentPlayer
                        completedCount++
                    }
                }
            }
        } else {
            // Check box to the left
            if (col > 0) {
                if (verticalLines[row][col - 1] != null && 
                    horizontalLines[row][col - 1] != null && 
                    horizontalLines[row + 1][col - 1] != null &&
                    verticalLines[row][col] != null) {
                    if (newBoxes[row][col - 1] == null) {
                        newBoxes[row][col - 1] = currentPlayer
                        completedCount++
                    }
                }
            }
            // Check box to the right
            if (col < size) {
                if (verticalLines[row][col] != null && 
                    horizontalLines[row][col] != null && 
                    horizontalLines[row + 1][col] != null &&
                    verticalLines[row][col + 1] != null) {
                    if (newBoxes[row][col] == null) {
                        newBoxes[row][col] = currentPlayer
                        completedCount++
                    }
                }
            }
        }

        if (completedCount > 0) {
            boxes = newBoxes
            if (currentPlayer == Player.RED) redScore += completedCount else blueScore += completedCount
            return true
        }
        return false
    }

    private fun handleTurnEnd(boxesCompleted: Boolean) {
        if (!boxesCompleted) {
            currentPlayer = if (currentPlayer == Player.RED) Player.BLUE else Player.RED
        }

        if (redScore + blueScore == size * size) {
            winner = if (redScore > blueScore) Player.RED else if (blueScore > redScore) Player.BLUE else null
        } else if (playerCount == 1 && currentPlayer == Player.BLUE && winner == null) {
            viewModelScope.launch {
                delay(600)
                makeAiMove()
            }
        }
    }

    private fun makeAiMove() {
        // Simple AI
        // 1. Look for a line that completes a box
        for (r in 0..size) {
            for (c in 0 until size) {
                if (horizontalLines[r][c] == null && wouldCompleteBox(r, c, isHorizontal = true)) {
                    makeHorizontalMove(r, c)
                    return
                }
            }
        }
        for (r in 0 until size) {
            for (c in 0..size) {
                if (verticalLines[r][c] == null && wouldCompleteBox(r, c, isHorizontal = false)) {
                    makeVerticalMove(r, c)
                    return
                }
            }
        }

        // Random move
        val hMoves = mutableListOf<Pair<Int, Int>>()
        for (r in 0..size) for (c in 0 until size) if (horizontalLines[r][c] == null) hMoves.add(r to c)
        
        val vMoves = mutableListOf<Pair<Int, Int>>()
        for (r in 0 until size) for (c in 0..size) if (verticalLines[r][c] == null) vMoves.add(r to c)
        
        if (hMoves.isNotEmpty() && (vMoves.isEmpty() || (0..1).random() == 0)) {
            val move = hMoves.random()
            makeHorizontalMove(move.first, move.second)
        } else if (vMoves.isNotEmpty()) {
            val move = vMoves.random()
            makeVerticalMove(move.first, move.second)
        }
    }

    private fun wouldCompleteBox(row: Int, col: Int, isHorizontal: Boolean): Boolean {
        if (isHorizontal) {
            if (row > 0 && horizontalLines[row - 1][col] != null && verticalLines[row - 1][col] != null && verticalLines[row - 1][col + 1] != null) return true
            if (row < size && horizontalLines[row + 1][col] != null && verticalLines[row][col] != null && verticalLines[row][col + 1] != null) return true
        } else {
            if (col > 0 && verticalLines[row][col - 1] != null && horizontalLines[row][col - 1] != null && horizontalLines[row + 1][col - 1] != null) return true
            if (col < size && verticalLines[row][col + 1] != null && horizontalLines[row][col] != null && horizontalLines[row + 1][col] != null) return true
        }
        return false
    }

    fun resetGame() {
        horizontalLines = Array(size + 1) { arrayOfNulls<Player>(size) }
        verticalLines = Array(size) { arrayOfNulls<Player>(size + 1) }
        boxes = Array(size) { Array<Player?>(size) { null } }
        currentPlayer = Player.RED
        blueScore = 0
        redScore = 0
        winner = null
    }
}
