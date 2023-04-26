@file:OptIn(ExperimentalFoundationApi::class)

package com.example.minesweeper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.minesweeper.engine.GenerateInitData
import com.example.minesweeper.engine.MinefieldManager
import com.example.minesweeper.ui.theme.MinesweeperTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var minefieldManager = MinefieldManager(GenerateInitData(percentOfBomb = 20))
        setContent {
            MinesweeperTheme {
                val viewModel = viewModel<MinesweeperViewModel>()
                // A surface container using the 'background' color from the theme
                viewModel.initModel(minefieldManager)
                val state = viewModel.gameBoardState
                val gameState = viewModel.gameState
                viewModel.minefieldManager = minefieldManager
                Minefield(gameState = gameState, state = state, onAction = viewModel::onAction, minefieldManager = minefieldManager)
            }
        }
    }
}

