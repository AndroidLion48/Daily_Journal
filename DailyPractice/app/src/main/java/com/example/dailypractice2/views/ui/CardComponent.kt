@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.example.dailypractice2.views.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dailypractice2.models.JournalEntryModel
import com.example.dailypractice2.views.ui.theme.DailyPractice2Theme

/**
 * Created by Clarence E Moore on 2025-09-16.
 *
 * Description: This file contains the composable function for displaying a single journal entry
 * as a card. It utilizes shared transitions for animations when navigating between
 * a list of entries and a detailed view of an entry.
 */

/**
 * Displays a card for a single journal entry.
 *
 * This composable is designed to be used within a [SharedTransitionScope] and
 * [AnimatedVisibilityScope] to enable animations when the card appears or disappears,
 * or when transitioning to a detailed view.
 *
 * @param journal The JournalEntryModel to display.
 * @param modifier Modifier for this composable.
 * @param animatedVisibilityScope The scope for animated visibility transitions.
 * @param onItemClick Callback invoked when the card is clicked, providing the index of the item.
 * @param index The index of this journal entry in the list, used for the click callback.
 */
@Composable
fun SharedTransitionScope.CardComponent(
    journal: JournalEntryModel,
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onItemClick: (index: Int) -> Unit,
    index: Int,
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable { onItemClick(index) },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        CardTitleComponent(
            journal = journal,
            animatedVisibilityScope = animatedVisibilityScope
        )
        CardContentComponent(
            journal = journal,
            animatedVisibilityScope = animatedVisibilityScope,
            modifier = Modifier
        )
        CardDateComponent(
            journal = journal,
            animatedVisibilityScope = animatedVisibilityScope,
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0x000000)
@Composable
fun JournalEntryCardPreview() {
    DailyPractice2Theme {

        val previewObject = JournalEntryModel(
            title = "Sample Title",
            content = "Mauris id pulvinar tellus, eget malesuada ante. Vivamus tellus massa, posuere at lacinia at, tincidunt non tellus. Proin lacinia felis eget mauris vehicula, vel condimentum felis pellentesque. Sed at dapibus ipsum. Nullam consectetur, ipsum dictum interdum pharetra, ipsum neque hendrerit ipsum, at hendrerit ante dui in dolor.",
            date = "2025-05-27",
            id = 1
        )

        SharedTransitionLayout {
            AnimatedVisibility(
                visible = true,
                label = "PreviewVisibility",
            ) {
                CardComponent(
                    journal = previewObject,
                    onItemClick = {},
                    index = 0,
                    animatedVisibilityScope = this,
                )
            }
        }
    }
}
