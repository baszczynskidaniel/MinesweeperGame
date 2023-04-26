package com.example.minesweeper

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.minesweeper.engine.FieldState
import com.example.minesweeper.engine.GenerateInitData
import com.example.minesweeper.engine.MinefieldManager

class MinesweeperViewModel: ViewModel() {
    val gameBoardState = mutableStateListOf<FieldState>()
    var gameState by  mutableStateOf(GameState())
    private set

    var minefieldManager: MinefieldManager? = null
    var isFirstMove: Boolean = true

    fun initModel(minefieldManager: MinefieldManager)
    {
        this.minefieldManager = minefieldManager
        gameBoardState.apply { repeat(minefieldManager.generateInitData.numberOfFields) {add(FieldState.COVER)} }
        gameState = gameState.copy(remainingBombs = minefieldManager.remainingBombs)
    }
    fun updateGameBoardState()
    {
        for(id in 0 until minefieldManager!!.generateInitData.numberOfFields)
        {
            gameBoardState[id] = minefieldManager!!.fieldAtId[id]!!.state
        }
    }
    fun onAction(action: MinesweeperAction)
    {
        when(action)
        {
            is MinesweeperAction.PressField -> onPress(action.index)
            is MinesweeperAction.ClickFiled -> minefieldClick(action.index)
            is MinesweeperAction.ChangeOption -> {gameState = gameState.copy(isInOptionMenu = true)}
            is MinesweeperAction.StartNewGame -> {newGame()}
            is MinesweeperAction.SettingsChangesDismiss -> {gameState = gameState.copy(isInOptionMenu = false)}
            is MinesweeperAction.ApplyChanges -> {setNewGameWithNewGenerateOption(action.row, action.col, action.percentOfBomb)}
        }
    }
    fun setNewGameWithNewGenerateOption(row: Int, col: Int, percent: Int)
    {
        minefieldManager!!.generateInitData =  GenerateInitData(rowNumber = row, colNumber = col, percentOfBomb = percent, firstUncoverField = null)
        Log.d("row", "$row")
        Log.d("col", "$col")
        Log.d("percent", "$percent")
        gameState = gameState.copy(isInOptionMenu = false)
        gameBoardState.clear()
        gameBoardState.apply { repeat(minefieldManager!!.generateInitData.numberOfFields) {add(FieldState.COVER)} }
        newGame()
    }

    fun newGame()
    {
        minefieldManager!!.generate(0 ,0)
        isFirstMove = true
        gameState = gameState.copy(minefieldManager!!.remainingBombs,false, isWin = false )
        updateGameBoardState()
    }


    fun generateIfFirstMove(id: Int)
    {
        if(isFirstMove) {
            minefieldManager!!.generate(
                minefieldManager!!.fieldAtId[id]!!.row,
                minefieldManager!!.fieldAtId[id]!!.col
            )

            isFirstMove = false
        }
    }

    fun uncoverField(id: Int)
    {

        minefieldManager!!.uncoverField(id)
        updateGameBoardState()
        if(minefieldManager!!.isBombHit)
        {
            gameState = gameState.copy(isBombHit = true)
        }
        if(minefieldManager!!.numberOfFieldsToUncover == 0)
            gameState = gameState.copy(isWin = true)
    }
    fun uncoverFieldsAroundIfNumberOfFlagsEqualFieldValue(id: Int)
    {
        if(minefieldManager!!.isNumberOfFlagsAroundEqualsFieldValue(id))
            minefieldManager!!.uncoverAroundField(id)

        updateGameBoardState()
        if(minefieldManager!!.isBombHit)
        {
            gameState = gameState.copy(isBombHit = true)
        }
        if(minefieldManager!!.numberOfFieldsToUncover == 0)
            gameState = gameState.copy(isWin = true)
    }

    fun minefieldClick(id: Int)
    {
        generateIfFirstMove(id)
        when(minefieldManager!!.fieldAtId[id]?.state)
        {
            FieldState.FLAGGED -> {}
            FieldState.COVER -> {uncoverField(id)}
            FieldState.UNCOVER -> {uncoverFieldsAroundIfNumberOfFlagsEqualFieldValue(id)}
        }
        val field = gameBoardState[id]
        gameBoardState[id] = minefieldManager!!.fieldAtId[id]!!.state

        if(minefieldManager!!.numberOfFieldsToUncover == 0)
            gameState = gameState.copy(isWin = true)

        Log.d("win", "${minefieldManager!!.numberOfFieldsToUncover}")
    }
    fun placeFlag(id: Int)
    {
        minefieldManager!!.placeFlag(minefieldManager!!.fieldAtId[id]!!.row, minefieldManager!!.fieldAtId[id]!!.col)
        gameState = gameState.copy(remainingBombs = minefieldManager!!.remainingBombs)
    }
    fun removeFlag(id: Int)
    {
        minefieldManager!!.removeFlag(minefieldManager!!.fieldAtId[id]!!.row, minefieldManager!!.fieldAtId[id]!!.col)
        gameState = gameState.copy(remainingBombs = minefieldManager!!.remainingBombs)
    }

    fun onPress(id: Int)
    {
        generateIfFirstMove(id)
        when(minefieldManager!!.fieldAtId[id]?.state)
        {
            FieldState.FLAGGED -> {removeFlag(id)}
            FieldState.COVER -> {placeFlag(id)}
            FieldState.UNCOVER -> {}
        }
        val field = gameBoardState[id]
        gameBoardState[id] = minefieldManager!!.fieldAtId[id]!!.state
    }
}