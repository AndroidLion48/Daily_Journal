@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.example.dailypractice2.views.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailypractice2.models.JournalEntryModel
import com.example.dailypractice2.views.ui.theme.DailyPractice2Theme

/**
 * Created by Clarence E Moore on 2025-09-16.
 *
 * Description: A composable function that displays the content of a journal entry.
 * It is designed to be used within a [SharedTransitionScope] and [AnimatedVisibilityScope]
 * to enable shared element transitions for the content text.
 */

/**
 * Displays the content of a journal entry with shared element transition capabilities.
 *
 * @param journal The [JournalEntryModel] containing the data to be displayed.
 * @param modifier Optional [Modifier] to be applied to the Text composable.
 * @param animatedVisibilityScope The scope of the [AnimatedVisibility] managing this composable's appearance.
 *                                This is crucial for shared element transitions.
 */
@Composable
fun SharedTransitionScope.CardContentComponent(
    journal: JournalEntryModel,
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
){
    // Text composable to display the journal content.
    Text(
        text = journal.content,
        textAlign = TextAlign.Justify,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        fontStyle = FontStyle.Italic,
        overflow = TextOverflow.Ellipsis, // Add ellipsis (...) if the text overflows
        modifier = modifier
            // Fills the maximum width available to the composable.
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 8.dp,
                top = 8.dp
            )
            // Enables shared element transition for this Text composable.
            // The key for the shared element is uniquely generated based on the journal's ID.
            // The boundsTransform defines the animation for the transition.
            .sharedElement(
                state = rememberSharedContentState(key = "content_${journal.id}"),
                animatedVisibilityScope = animatedVisibilityScope,
                boundsTransform = { _, _ ->
                    // Specifies a tween animation with a duration of 1000 milliseconds for the transition.
                    tween(durationMillis = 1000)
                }
            ),
    )
}

/**
 * A preview function for the [CardContentComponent].
 * This allows for easy visualization of the component in Android Studio's preview pane.
 */
@Preview(showBackground = true)
@Composable
private fun CardContentComponentPreview() {
    DailyPractice2Theme {
        // Sample data for the preview.
        val previewJournalEntry = JournalEntryModel(
            id = 1,
            title = "Sample Title",
            content = "This is a sample content for the journal entry. It provides a brief overview of the entry's main points and themes.",
            date = "2023-10-01"
        )

        SharedTransitionScope {
            // AnimatedVisibility is required for the shared element transition to work in the preview.
            AnimatedVisibility(
                visible = true,
                label = "PreviewVisibility",
            ) {
                // Calling the CardContentComponent with sample data.
                CardContentComponent(
                    journal = previewJournalEntry,
                    animatedVisibilityScope = this
                )
            }
        }
    }
}
