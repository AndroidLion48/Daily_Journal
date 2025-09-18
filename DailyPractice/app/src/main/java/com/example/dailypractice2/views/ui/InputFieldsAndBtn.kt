package com.example.dailypractice2.views.ui

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dailypractice2.data.JournalRepositories
import com.example.dailypractice2.data.entity.JournalEntryEntity
import com.example.dailypractice2.views.JournalListActivity
import com.example.dailypractice2.views.viewModels.JournalViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Created by Clarence E Moore on 2025-09-18.
 *
 * Description: This composable function represents the input fields for title, content,
 * and a button to save a journal entry. It also displays the current date.
 */

/**
 * Composable function for input fields and a save button for journal entries.
 *
 * @param modifier Modifier for customizing the layout and appearance of the composable.
 * @param journalViewModel ViewModel for managing journal entries.
 */
@Composable
fun InputFieldsAndBtn(
    modifier: Modifier = Modifier,
    journalViewModel: JournalViewModel,
) { // Start of InputFieldsAndBtn composable function

    // State variables for title and content input fields
    var title: String by remember { mutableStateOf("") }
    var content: String by remember { mutableStateOf("") }

    // Get the current context and formatted date
    val currentDate = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(Date())
    val context = LocalContext.current

    // Column layout for arranging input fields and button vertically
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Text field for entering the title
        TitleTextField(
            value = title,
            onValueChange = { title = it },
            label = "Enter Title Here",
        )
        // Display field for the current date
        DateDisplayField(
            date = currentDate,
            label = "Date",
        )
        ContentTextField(
            // Text field for entering the content
            value = content,
            onValueChange = { content = it },
            label = "Enter Content Here",
            lines = 5,
        )
        Button(
            onClick = {
                if (title.isNotEmpty() && content.isNotEmpty()) {
                    // Add the journal entry to the ViewModel
                    journalViewModel
                        .addEntry(
                            title = title,
                            content = content,
                        )

                    // Clear the input fields after saving
                    title = ""
                    content = ""

                    val intent = Intent(context, JournalListActivity::class.java)
                    context.startActivity(intent)

                    Toast.makeText(context, "Journal Entry Saved", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Please make entry in Title and Context Fields.", Toast.LENGTH_LONG).show()
                }
            },
            modifier = Modifier.Companion
                .align(Alignment.Companion.CenterHorizontally),
        ) {
            Text(
                // Text for the save button
                text = "Save Entry"
            )
        }
    }
} // End of InputFieldsAndBtn composable function

/**
 * Preview function for the InputFieldsAndBtn composable.
 */
@Preview(showBackground = true)
@Composable
fun InputFieldsAndBtnPreview() {
    // Fake repository for preview purposes
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

    // Create a fake repository and ViewModel for preview
    val fakeRepository: JournalRepositories = FakeJournalRepository()
    val fakeJournalViewModel = JournalViewModel(fakeRepository)

    // Display the InputFieldsAndBtn composable with the fake ViewModel
    InputFieldsAndBtn(
        journalViewModel = fakeJournalViewModel,
    )
} // End of InputFieldsAndBtnPreview composable function
