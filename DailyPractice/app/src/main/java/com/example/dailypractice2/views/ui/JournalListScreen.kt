@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.example.dailypractice2.views.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dailypractice2.models.JournalEntryModel
import com.example.dailypractice2.views.ui.theme.DailyPractice2Theme

/**
 * Created by Clarence E Moore on 2025-09-17.
 *
 * Description: This composable function represents the main screen for displaying a list of journal entries.
 * It utilizes a [SharedTransitionScope] to enable shared element transitions between this screen and
 * the details screen. The screen includes a list of journal entry cards and a button to add new entries.
 *
 */

@Composable
fun SharedTransitionScope.JournalListScreen(
    modifier: Modifier = Modifier,
    /**
     * The list of journal entries to be displayed.
     */
    journalEntriesList: List<JournalEntryModel>,
    /**
     * The scope for animated visibility, used for transitions.
     */
    animatedVisibilityScope: AnimatedVisibilityScope,
    /**
     * Callback function invoked when a journal entry item is clicked. It receives the index of the clicked item.
     */
    onItemClick: (index: Int) -> Unit,
    /**
     * Callback function invoked when the "Add Entry" button is clicked.
     */
    onAddEntryClick: () -> Unit,
) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(top = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CardListComponent(
                journalEntriesList = journalEntriesList,
                animatedVisibilityScope = animatedVisibilityScope,
                onItemClick = onItemClick,
                modifier = Modifier.weight(1f)
            )
            BtnAddEntryComponent(
                onAddEntryClick = onAddEntryClick,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun JournalListScreenPreview() {
    DailyPractice2Theme {

        val previewObject = JournalEntryModel(
            id = 1,
            title = "Title 1",
            content = "This is a sample content for the journal entry. It provides a brief overview of the entry's main points and themes.",
            date = "Date 1"
        )

        val previewObjectList = listOf(
            previewObject.copy(id = 401, title = "First Entry"),
            previewObject.copy(id = 402, title = "Second Entry"),
            previewObject.copy(id = 403, title = "Third Entry"),
        )

        SharedTransitionLayout{
            AnimatedVisibility(
                visible = true,
                label = "PreviewVisibility",
            ) {
                JournalListScreen(
                    journalEntriesList = previewObjectList,
                    onItemClick = {},
                    onAddEntryClick = {},
                    animatedVisibilityScope = this
                )
            }
        }
    }
}
