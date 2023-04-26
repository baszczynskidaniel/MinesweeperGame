package com.example.minesweeper.engine

data class Field(
    var state: FieldState = FieldState.COVER,
    var value: Int = 0,
    var id: Int = 0,
    var row: Int = 0,
    var col: Int = 0
)
