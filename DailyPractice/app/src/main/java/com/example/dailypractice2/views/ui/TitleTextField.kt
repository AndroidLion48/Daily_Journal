package com.example.dailypractice2.views.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Created by Clarence E Moore on 2025-09-17.
 *
 * Description: A composable function that displays an OutlinedTextField with specific styling
 * for titles. It allows for text input with customizable label, keyboard options, and behavior.
 */

/**
 * A composable function that creates an OutlinedTextField styled for titles.
 *
 * @param value The current text value of the TextField.
 * @param onValueChange A callback that is triggered when the text value changes.
 * @param label The label text to display above the TextField.
 * @param keyboardOptions The keyboard options for the TextField, defaults to KeyboardOptions.Default.
 */
@Composable
fun TitleTextField(
    value: String, // The current text in the TextField
    onValueChange: (String) -> Unit, // Callback for when the text changes
    label: String, // The label for the TextField
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default, // Keyboard options
){

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(
            label,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.Magenta,
        ) },
        maxLines = 2,
        textStyle = TextStyle(color = Color.Green, fontWeight = FontWeight.SemiBold),
        keyboardOptions = keyboardOptions.copy(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next,
        ),
        modifier = Modifier.Companion
            .fillMaxWidth()
            .padding(8.dp),
    )
}

/**
 * A preview function for the TitleTextField composable.
 */
@Preview(showBackground = true)
@Composable
fun TitleTextFieldPreview() {
    TitleTextField(
        value = "Sample Title",
        onValueChange = {},
        label = "Title",
    )
}
