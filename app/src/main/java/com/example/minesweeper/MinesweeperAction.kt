package com.example.minesweeper

sealed class MinesweeperAction {
    data class ClickFiled(val index: Int): MinesweeperAction()
    data class PressField(val index: Int): MinesweeperAction()
    object StartNewGame: MinesweeperAction()
    object ChangeOption: MinesweeperAction()
    object SettingsChangesDismiss: MinesweeperAction()
    data class ApplyChanges(val row: Int, val col: Int,val percentOfBomb: Int): MinesweeperAction()

}