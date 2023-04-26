package com.example.minesweeper

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.minesweeper.ui.theme.Mine0

@Composable
fun OptionField
            (
    onAction: (MinesweeperAction) -> Unit,
    text: String,
    colorText: Color,
    colorTextField: Color = Mine0,
    variable: MutableState<String>
            )
{
    var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    )
    {
        Text(text = text,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(220.dp)
                .height(70.dp)
                .clip(RoundedCornerShape(50))
                .background(colorText),

            fontSize = 30.sp,
            color = Color.Black,
        )
        TextField(
            value = textFieldValue,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = { newText ->
                textFieldValue = newText
                variable.value = textFieldValue.text
            } ,
            singleLine = true,
            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 30.sp,
                textAlign = TextAlign.Start),
            modifier = Modifier
                .height(70.dp)
                .width(70.dp)
                .clip(RoundedCornerShape(100))
                .background(colorTextField),
        )
    }
}