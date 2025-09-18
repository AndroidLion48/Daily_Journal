package com.example.dailypractice2.views.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Created by Clarence E Moore on 2025-09-18.
 *
 * Description: This Composable function creates a customizable text field for content input.
 * It allows for specifying the initial value, a callback for value changes, a label,
 * and the number of lines the text field should display.
 * The text field is styled with an outline and specific font characteristics for the label.
 */

/**
 * A Composable function that displays an OutlinedTextField suitable for entering multi-line content.
 *
 * @param modifier Modifier for this text field.
 * @param value The input text to be shown in the text field.
 * @param onValueChange The callback that is triggered when the input service updates the text. An
 * updated text comes as a parameter of the callback.
 * @param label The label to be displayed inside the text field container.
 * @param lines The approximate number of lines the text field should display. The height is
 * calculated based on this number.
 */
@Composable
fun ContentTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    lines: Int = 5,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(
            label,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.Magenta
        ) },
        modifier = modifier
            .height((lines * 30).dp) // Approximate height for given number of lines
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    )
}

/**
 * A preview function for the [ContentTextField] Composable.
 */
@Preview(showBackground = true)
@Composable
private fun ContentTextFieldPreview() {


    ContentTextField(
        value = "Sample Content Looks like this...",
        onValueChange = {},
        label = "Enter Content Here",
    )
}
