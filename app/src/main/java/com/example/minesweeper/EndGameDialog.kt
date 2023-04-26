package com.example.minesweeper

import androidx.compose.foundation.layout.Column
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun endGameDialog(
    onAction: (MinesweeperAction) -> Unit,
    title: String,
) {
        Column {
            val openDialog = remember { mutableStateOf(true) }
            if (openDialog.value) {
                AlertDialog(
                    onDismissRequest = {
                        openDialog.value = true
                    },
                    title = {
                        Text(text = title)
                    },
                    confirmButton = {},
                    dismissButton = {
                        Button(
                            onClick = {
                                openDialog.value = false
                                onAction(MinesweeperAction.StartNewGame)
                            }) {
                            Text("Start new game")
                        }
                    }
                )
            }
        }
    }
