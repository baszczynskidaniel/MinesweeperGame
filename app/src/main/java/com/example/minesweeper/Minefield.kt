package com.example.minesweeper

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.minesweeper.engine.FieldState
import com.example.minesweeper.engine.MinefieldManager

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Minefield(
    state: SnapshotStateList<FieldState>,
    gameState: GameState,
    onAction: (MinesweeperAction) -> Unit,
    modifier: Modifier = Modifier,
    minefieldManager: MinefieldManager
) {
    var fieldColor : Color
    Box() {
        if(gameState.isBombHit) {
            endGameDialog(onAction = onAction, title = "You hit the bomb")
        }
        if(gameState.isWin)
        {
            endGameDialog(onAction = onAction, title = "You win congratulation")
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black)
                .align(Alignment.TopCenter),
            )
        {
            if(gameState.isInOptionMenu){
               optionMenu(onAction = onAction)
            }
            else{
            Row( modifier = Modifier
                .background(color = Color.Black)
               )
            {
                Text(
                    text = "${gameState.remainingBombs}",
                    fontSize = 40.sp,
                    color = Color.White
                )
                Button(onClick = { onAction(MinesweeperAction.ChangeOption )
                })

                {
                    Icon(Icons.Default.Edit, null, tint = Color.White)
                }
            }

        LazyVerticalGrid(
            cells = GridCells.Fixed(minefieldManager.generateInitData.colNumber),
            content = {
                items(minefieldManager.generateInitData.numberOfFields) { i ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .aspectRatio(1f)
                            .background(Color.Black)
                            .padding(1.dp)
                            .clip(CircleShape),
                        contentAlignment = Alignment.Center
                    )
                    {
                        val interactionSource = remember {
                            MutableInteractionSource()
                        }
                        val isPressed by interactionSource.collectIsPressedAsState()
                        Button(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentPadding = PaddingValues(0.dp),
                            interactionSource = interactionSource,
                            onClick = {

                                if (isPressed) {
                                    onAction(MinesweeperAction.PressField(i))
                                } else {
                                    onAction(MinesweeperAction.ClickFiled(i))
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(
                                    33,
                                    33,
                                    33
                                )
                            ),
                        )
                        {

                            var backgroundColor: Color = Color.White
                            when (minefieldManager.getFileAtId(i).value) {
                                0 -> backgroundColor = Color(0xE3, 0xE3, 0xE3)
                                1 -> backgroundColor = Color(0xCC, 0xFF, 0x61)
                                2 -> backgroundColor = Color(0x00, 0xFF, 0x94)
                                3 -> backgroundColor = Color(0x65, 0xFF, 0xFF)
                                4 -> backgroundColor = Color(0x59, 0xAF, 0xFE)
                                5 -> backgroundColor = Color(0xCA, 0x86, 0xFF)
                                6 -> backgroundColor = Color(0xFF, 0x7A, 0xDA)
                                7 -> backgroundColor = Color(0xFF, 0x78, 0x98)
                                8 -> backgroundColor = Color(0xED, 0x40, 0x55)
                                9 -> backgroundColor = Color.Red
                            }
                            var textColor: Color = Color.Black

                            if (minefieldManager.getFileAtId(i).value == Constants.NO_BOMBS_AROUND)
                                textColor = Color(0xE3, 0xE3, 0xE3)


                            when (state[i]) {
                                FieldState.COVER -> {

                                }
                                FieldState.UNCOVER -> {

                                    if (minefieldManager.getFileAtId(i).state == FieldState.UNCOVER && minefieldManager.getFileAtId(
                                            i
                                        ).value != Constants.BOMB_VALUE
                                    ) {
                                        Text(
                                            text = "${minefieldManager.getFileAtId(i).value}",
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(backgroundColor),
                                            textAlign = TextAlign.Center,
                                            fontSize = 30.sp,
                                            color = textColor,
                                        )
                                    }
                                    if (minefieldManager.getFileAtId(i).state == FieldState.UNCOVER && minefieldManager.getFileAtId(
                                            i
                                        ).value == Constants.BOMB_VALUE
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.bomb_svgrepo_com_1_),
                                            contentDescription = null
                                        )
                                    }
                                }
                                FieldState.FLAGGED -> {

                                    Image(
                                        painter = painterResource(id = R.drawable.flag_flag_pole_svgrepo_com),
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    }
                }
            })
      }}}}
