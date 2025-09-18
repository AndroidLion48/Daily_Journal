package com.example.dailypractice2.views.ui

import androidx.compose.foundation.layout.fillMaxWidth
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
 * Description: A composable function that displays a date in an OutlinedTextField.
 * This field is read-only and is styled with a magenta label.
 *
 */

/**
 * A composable function that displays a date in a read-only OutlinedTextField.
 *
 * @param modifier Modifier for this composable.
 * @param date The date string to display.
 * @param label The label for the OutlinedTextField.
 */
@Composable
fun DateDisplayField(
    modifier: Modifier = Modifier,
    date: String,
    label: String,
){
    // OutlinedTextField is used to display the date.
    // It's set to read-only as this is a display field.
    OutlinedTextField(
        value = date,
        onValueChange = {},
        readOnly = true,
        label = { Text(
            label,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.Magenta,
        ) },
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
    )
}

/**
 * Preview function for the DateDisplayField composable.
 */
@Preview(showBackground = true)
@Composable
fun DateDisplayFieldPreview() {
    DateDisplayField(
        label = "Date",
        date = "09/18/2025",
    )
}
