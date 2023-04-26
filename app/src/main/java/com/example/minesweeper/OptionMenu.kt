package com.example.minesweeper

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.minesweeper.ui.theme.*
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun optionMenu(onAction: (MinesweeperAction) -> Unit) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly

    ) {
        var row = remember {
            mutableStateOf("")
        }
        var col = remember {
            mutableStateOf("")
        }
        var percent = remember {
            mutableStateOf("")
        }
        OptionField(
            variable = row,
            colorText = Mine1,
            onAction = onAction,
            text = "row number: "
        )
        OptionField(
            variable = col,
            colorText = Mine4,
            onAction = onAction,
            text = "column number: "
        )
        OptionField(
            variable = percent,
            colorText = Mine7,
            onAction = onAction,
            text = "percent of bomb: "
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center)
        {
            Button(onClick = {onAction(MinesweeperAction.SettingsChangesDismiss)},  modifier = Modifier
                .clip(RoundedCornerShape(100))
                .size(100.dp, 40.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.DarkGray
                )
            ) {
                Icon(Icons.Default.Close, tint = Mine0, contentDescription = null)
            }
            Button(onClick = {
                var intRow: Int? = row.value.toIntOrNull()
                var intCol: Int? = col.value.toIntOrNull()
                var intPercent: Int? = percent.value.toIntOrNull()

                if(intRow!! > 0 && intRow!! < 100 && intCol!! > 0 && intCol!! < 100 && intPercent!! > 0 && intPercent!! < 100)
                {
                     onAction(MinesweeperAction.ApplyChanges(intRow, intCol, intPercent))
                }
            },  modifier = Modifier
                .clip(RoundedCornerShape(100))
                .size(100.dp, 40.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.DarkGray
                    )

                ) {
                    Icon(Icons.Default.Check, tint = Mine0, contentDescription = null)
            }
        }
    }
}