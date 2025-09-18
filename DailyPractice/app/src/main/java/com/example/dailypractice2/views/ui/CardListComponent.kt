@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.example.dailypractice2.views.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
 *
 *
 * This Composable function, `CardListComponent`, is designed to display a list of journal entries
 * as cards. It leverages `SharedTransitionScope` for animated transitions between list and detail
 * views.
 *
 * The component takes a list of `JournalEntryModel` objects, an item click listener, and an
 * `AnimatedVisibilityScope` to manage animations. It uses `LazyColumn` for efficient rendering
 * of the list items, and `CardComponent` to display each individual journal entry.
 *
 * The preview function `CardListComponentPreview` demonstrates how to use `CardListComponent`
 * within a `SharedTransitionLayout` and `AnimatedVisibility` for a complete example.
 */
@Composable
fun SharedTransitionScope.CardListComponent(
    // Modifier for customizing the layout
    modifier: Modifier = Modifier,
    journalEntriesList: List<JournalEntryModel>,
    onItemClick: (index: Int) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
){
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(journalEntriesList.size) { index ->
            CardComponent(
                index = index,
                journal = journalEntriesList[index],
                onItemClick = onItemClick,
                modifier = Modifier,
                animatedVisibilityScope = animatedVisibilityScope,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CardListComponentPreview() {
    DailyPractice2Theme {

        val previewObject = JournalEntryModel(
            id = 1,
            title = "Title 1",
            content = "This is a sample content for the journal entry. It provides a brief overview of the entry's main points and themes.",
            date = "Date 1"
        )

        SharedTransitionLayout{
            AnimatedVisibility(
                visible = true,
                label = "PreviewVisibility",
            ) {
                CardListComponent(
                    journalEntriesList = listOf(
                        previewObject.copy(id = 401, title = "First Entry"),
                        previewObject.copy(id = 402, title = "Second Entry"),
                        previewObject.copy(id = 403, title = "Third Entry"),
                    ),
                    onItemClick = {},
                    animatedVisibilityScope = this
                )
            }
        }
    }
}
