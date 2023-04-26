package com.example.minesweeper

data class GameState(
    val remainingBombs: Int = 0,
    val isBombHit: Boolean = false,
    val isInOptionMenu: Boolean = false,
    val isWin: Boolean = false
    ) {}