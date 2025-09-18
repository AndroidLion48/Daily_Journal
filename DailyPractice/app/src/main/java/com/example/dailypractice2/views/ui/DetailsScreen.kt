@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.example.dailypractice2.views.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dailypractice2.models.JournalEntryModel
import com.example.dailypractice2.views.ui.theme.DailyPractice2Theme

/**
 * Created by Clarence E Moore on 2025-09-17.
 *
 * Description:
 * This composable function represents the details screen of a journal entry.
 * It displays the title, date, and content of a selected journal entry.
 * It utilizes shared element transitions for a smoother user experience when navigating
 * to this screen.
 */

/**
 * Composable function to display the details of a selected journal entry.
 *
 * @param modifier Modifier for styling and layout.
 * @param selectedEntry The [JournalEntryModel] to display.
 * @param animatedVisibilityScope The scope for animated visibility, used for shared element transitions.
 * @param onItemClick Lambda function to be invoked when the details screen is clicked.
 */
@Composable
fun SharedTransitionScope.DetailsScreen(
    modifier: Modifier,
    selectedEntry: JournalEntryModel,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onItemClick: (index: Int) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp, 38.dp, 16.dp, 16.dp)
            .clickable { onItemClick(selectedEntry.id) },
    ) {

        CardTitleComponent(
            journal = selectedEntry,
            animatedVisibilityScope = animatedVisibilityScope,
        )
        CardDateComponent(
            journal = selectedEntry,
            animatedVisibilityScope = animatedVisibilityScope,
        )
        CardContentComponent(
            journal = selectedEntry,
            animatedVisibilityScope = animatedVisibilityScope,
        )
    }
}

/**
 * Preview function for the DetailsScreen composable.
 */
@Preview(showBackground = true)
@Composable
private fun DetailsScreenPreview() {
    DailyPractice2Theme {
        SharedTransitionLayout{
            AnimatedVisibility(
                visible = true,
                label = "PreviewVisibility",
            ) {
                DetailsScreen(
                    modifier = Modifier,
                    selectedEntry = JournalEntryModel(
                        id = 1,
                        title = "Sample Title",
                        content = "This is a sample content for the journal entry. It provides a brief overview of the entry's main points.",
                        date = "2025-09-17"
                    ),
                    onItemClick = {},
                    animatedVisibilityScope = this,
                )
            }
        }
    }
}
