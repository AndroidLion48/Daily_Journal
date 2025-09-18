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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailypractice2.models.JournalEntryModel
import com.example.dailypractice2.views.ui.theme.DailyPractice2Theme

/**
 * Created by Clarence E Moore on 2025-09-16.
 *
 * Description: This composable function displays the title of a journal entry.
 * It utilizes SharedTransitionScope to enable shared element transitions between different screens
 * or states where this title might be displayed.
 *
 */

/**
 * A composable function that displays the title of a journal entry with shared element transition capabilities.
 *
 * This component is designed to be used within a [SharedTransitionScope] and an [AnimatedVisibilityScope]
 * to allow for smooth transitions of the title when navigating between different parts of the UI.
 *
 * @param journal The [JournalEntryModel] containing the data for the journal entry, including the title.
 * @param modifier A [Modifier] to be applied to this composable for styling and layout purposes.
 * Defaults to [Modifier].
 * @param animatedVisibilityScope The [AnimatedVisibilityScope] required for the shared element transition.
 * This scope is typically provided by an [AnimatedVisibility] composable.
 */
@Composable
fun SharedTransitionScope.CardTitleComponent(
    journal: JournalEntryModel,
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    // Displays the journal title.
    Text(
        text = journal.title,
        textAlign = TextAlign.Center,
        textDecoration = TextDecoration.Underline,
        fontSize = 24.sp,
        fontWeight = FontWeight.Companion.Bold,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            // Applies a shared element transition to this Text composable.
            // The key is unique for each journal entry ("title_${journal.id}").
            // The boundsTransform defines the animation timing (1000ms tween).
            .sharedElement(
                state = rememberSharedContentState(key = "title_${journal.id}"),
                animatedVisibilityScope = animatedVisibilityScope,
                boundsTransform = { _, _ -> tween(durationMillis = 1000) }),
    )
}

/**
 * A preview composable for the [CardTitleComponent].
 */
@Preview(showBackground = true)
@Composable
private fun CardTitleComponentPreview() {
    DailyPractice2Theme {

        val previewObject = JournalEntryModel(
            title = "Sample Title",
            content = "Mauris id pulvinar tellus, eget malesuada ante. Vivamus tellus massa, posuere at lacinia at, tincidunt non tellus. Proin lacinia felis eget mauris vehicula, vel condimentum felis pellentesque. Sed at dapibus ipsum. Nullam consectetur, ipsum dictum interdum pharetra, ipsum neque hendrerit ipsum, at hendrerit ante dui in dolor.",
            date = "2025-05-27",
            id = 1,
        )

        SharedTransitionLayout {
            AnimatedVisibility(
                visible = true,
                label = "PreviewVisibility",
            ) {
                CardTitleComponent(
                    journal = previewObject,
                    animatedVisibilityScope = this,
                    )
            }
        }
    }
}
