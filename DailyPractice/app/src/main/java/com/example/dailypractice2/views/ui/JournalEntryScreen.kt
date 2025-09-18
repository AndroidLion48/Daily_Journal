package com.example.dailypractice2.views.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailypractice2.data.JournalRepositories
import com.example.dailypractice2.data.entity.JournalEntryEntity
import com.example.dailypractice2.views.viewModels.JournalViewModel

/**
 * Created by Clarence E Moore on 2025-09-17.
 *
 * Description: This Composable function defines the UI for the Journal Entry screen.
 * It allows users to input their journal entries.
 *
 */

/**
 * Composable function for the Journal Entry Screen.
 *
 * @param modifier Modifier to be applied to the layout.
 * @param journalViewModel ViewModel for managing journal data.
 */
@Composable
fun JournalEntryScreen(
    modifier: Modifier = Modifier,
    journalViewModel: JournalViewModel,
) {
    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 40.dp, start = 16.dp, end = 16.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, Color.Green),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Enter Journal Here",
                textAlign = TextAlign.Companion.Center,
                fontWeight = FontWeight.Companion.Bold,
                textDecoration = TextDecoration.Underline,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
            )
            InputFieldsAndBtn(journalViewModel = journalViewModel)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun JournalEntryScreenPreview() {
    class FakeJournalRepository : JournalRepositories {
        override suspend fun upsertEntities(entities: List<JournalEntryEntity>) {
            TODO("Not yet implemented")
        }

        override suspend fun upsertEntity(entity: JournalEntryEntity) {
            TODO("Not yet implemented")
        }

        override suspend fun getAllEntities(): List<JournalEntryEntity> {
            TODO("Not yet implemented")
        }

        override suspend fun getEntityById(id: Long): JournalEntryEntity {
            TODO("Not yet implemented")
        }

        override suspend fun deleteEntity(entity: JournalEntryEntity) {
            TODO("Not yet implemented")
        }
    }

    val fakeRepository: JournalRepositories = FakeJournalRepository()
    val fakeJournalViewModel = JournalViewModel(fakeRepository)

    JournalEntryScreen(
        journalViewModel = fakeJournalViewModel
    )
}
