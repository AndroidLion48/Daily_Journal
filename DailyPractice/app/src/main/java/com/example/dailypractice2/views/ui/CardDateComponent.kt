@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.example.dailypractice2.views.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailypractice2.models.JournalEntryModel
import com.example.dailypractice2.views.ui.theme.DailyPractice2Theme

/**
 * Created by Clarence E Moore on 2025-09-16.
 *
 * Description: A composable function that displays the date of a journal entry.
 * This component is designed to be used within a [SharedTransitionScope] to enable
 * shared element transitions for the date text.
 *
 */

/**
 * Displays the date of a journal entry with a shared element transition.
 *
 * @param journal The [JournalEntryModel] containing the date to be displayed.
 * @param modifier Optional [Modifier] for customizing the layout and appearance of the component.
 * @param animatedVisibilityScope The scope of an [AnimatedVisibility] composable, required for shared element transitions.
 */
@Composable
fun SharedTransitionScope.CardDateComponent(
    /** The journal entry model containing the date. */
    journal: JournalEntryModel,
    /** Modifier for customizing the component. */
    modifier: Modifier = Modifier,
    /** The scope for animated visibility, enabling shared element transitions. */
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    Text(
        text = journal.date,
        textAlign = TextAlign.End,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = 8.dp,
                bottom = 16.dp,
                end = 24.dp
            )
            .sharedElement(
                state = rememberSharedContentState(key = "date_${journal.id}"),
                animatedVisibilityScope = animatedVisibilityScope,
                boundsTransform = { _, _ ->
                    tween(durationMillis = 1000)
                }
            ),
    )
}

@Preview(showBackground = true)
@Composable
private fun DateComponentPreview() {
    DailyPractice2Theme {
        val previewObject = JournalEntryModel(
            id = 1,
            title = "Sample Title",
            date = "2025-09-16",
            content = "This is a sample journal entry content for preview purposes."
        )

        SharedTransitionLayout {
            AnimatedVisibility(
                visible = true,
                label = "PreviewVisibility",
            ) {
                CardDateComponent(
                    journal = previewObject,
                    animatedVisibilityScope = this
                )
            }
        }
    }
}
