package com.yasinmoridi.miniverse.presentation.feature.snake_bite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

enum class Direction {
    UP, DOWN, LEFT, RIGHT
}

data class Point(val x: Int, val y: Int)

data class SnakeBiteState(
    val snake: List<Point> = listOf(Point(10, 10), Point(10, 11), Point(10, 12)),
    val food: Point = Point(5, 5),
    val bomb: Point = Point(15, 15),
    val direction: Direction = Direction.UP,
    val score: Int = 0,
    val isGameOver: Boolean = false,
    val gridSize: Int = 20,
    val gameSpeed: Long = 200
)

class SnakeBiteVM : ViewModel() {
    private val _state = MutableStateFlow(SnakeBiteState())
    val state = _state.asStateFlow()

    private var gameJob: kotlinx.coroutines.Job? = null

    init {
        startGame()
    }

    fun startGame() {
        gameJob?.cancel()
        _state.update { SnakeBiteState() }
        gameJob = viewModelScope.launch {
            while (!_state.value.isGameOver) {
                delay(_state.value.gameSpeed.milliseconds)
                moveSnake()
            }
        }
    }

    fun changeDirection(newDirection: Direction) {
        val currentDirection = _state.value.direction
        val isOpposite = when (newDirection) {
            Direction.UP -> currentDirection == Direction.DOWN
            Direction.DOWN -> currentDirection == Direction.UP
            Direction.LEFT -> currentDirection == Direction.RIGHT
            Direction.RIGHT -> currentDirection == Direction.LEFT
        }
        if (!isOpposite) {
            _state.update { it.copy(direction = newDirection) }
        }
    }

    private fun moveSnake() {
        _state.update { currentState ->
            val head = currentState.snake.first()
            val newHead = when (currentState.direction) {
                Direction.UP -> Point(head.x, head.y - 1)
                Direction.DOWN -> Point(head.x, head.y + 1)
                Direction.LEFT -> Point(head.x - 1, head.y)
                Direction.RIGHT -> Point(head.x + 1, head.y)
            }

            // Check collisions
            if (newHead.x !in 0..<currentState.gridSize || 
                newHead.y !in 0..<currentState.gridSize || 
                newHead in currentState.snake ||
                newHead == currentState.bomb) {
                return@update currentState.copy(isGameOver = true)
            }

            if (newHead == currentState.food) {
                val newScore = currentState.score + 1
                val newSpeed = (200 - (newScore * 5)).coerceAtLeast(100).toLong()
                currentState.copy(
                    score = newScore,
                    snake = listOf(newHead) + currentState.snake,
                    food = generateRandomPoint(currentState.gridSize, listOf(newHead) + currentState.snake),
                    bomb = generateRandomPoint(currentState.gridSize, listOf(newHead) + currentState.snake),
                    gameSpeed = newSpeed
                )
            } else {
                currentState.copy(
                    snake = listOf(newHead) + currentState.snake.dropLast(1)
                )
            }
        }
    }

    private fun generateRandomPoint(gridSize: Int, occupied: List<Point>): Point {
        var point: Point
        do {
            point = Point(Random.nextInt(gridSize), Random.nextInt(gridSize))
        } while (point in occupied)
        return point
    }
}
