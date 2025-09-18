package com.example.dailypractice2.views.ui

import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailypractice2.views.MainActivity
import com.example.dailypractice2.views.ui.theme.DailyPractice2Theme

/**
 * Created by Clarence E Moore on 2025-09-16.
 *
 * Description: A composable function that displays a button to add a new entry.
 * Clicking this button will navigate the user to the MainActivity.
 *
 */


/**
 * A composable function that renders a button labeled "Add Entry".
 *
 * This button, when clicked, initiates navigation to the `MainActivity` where users can
 * presumably add a new entry.
 *
 * @param modifier Optional [Modifier] to be applied to the button.
 * @param onAddEntryClick A lambda is used for NavHost navigation logic increasing flexibility
 *                        as needed in JournalListActivity.
 *                        While this parameter is present, the current implementation directly
 *                        navigates to `MainActivity`.
 */
@Composable
fun BtnAddEntryComponent(
    modifier: Modifier = Modifier,
    /** onAddEntryClick lambda is used for NavHost navigation logic
    *   increasing flexibility as needed in JournalListActivity.     */
    onAddEntryClick: () -> Unit,
) {
    val context = LocalContext.current
    Button(
        modifier = modifier
            .padding(16.dp),
        onClick = {

            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    ) {
        Text(
            text = "Add Entry",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
        )
    }
}

/**
 * A preview function for the [BtnAddEntryComponent].
 */
@Preview(showBackground = true)
@Composable
private fun BtnAddEntryComponentPreview() {
    DailyPractice2Theme {
        BtnAddEntryComponent(
            onAddEntryClick = {}
        )
    }
}
