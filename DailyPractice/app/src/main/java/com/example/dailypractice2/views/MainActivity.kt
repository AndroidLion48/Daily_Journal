package com.example.dailypractice2.views

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.dailypractice2.data.JournalRepositoriesImpl
import com.example.dailypractice2.data.database.JournalDatabase
import com.example.dailypractice2.views.theme.DailyPractice2Theme
import com.example.dailypractice2.views.viewModels.JournalViewModel
import com.example.dailypractice2.views.viewModels.JournalViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Created by Clarence E Moore on 2025-04-20.
 *
 * Description:
 * DailyPractice2 App v4.0.TEMP
 *
 * Content:
 * Has two activities:
 * 1. MainActivity
 * 2. JournalListActivity
 *
 * Practice:
 * Added an Activity and
 * a ViewModel for that Activity
 *
 *
 * Use to practice conversion and reverting to App v3.0.0 (Database Iteration)
 */

class MainActivity : ComponentActivity() {

    private val journalViewModel: JournalViewModel by lazy {
        val repository = JournalRepositoriesImpl(journalDatabase = JournalDatabase.getInstance(this))
        val factory = JournalViewModelFactory(repository)
        ViewModelProvider(this, factory)[JournalViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DailyPractice2Theme {
                Surface(
                    modifier = Modifier.Companion
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 8.dp)
                ) {
                    EnterJournalEntry()
                }
            }
        }
    }

    /**
     * Composable function to enter and save a new journal entry.
     *
     * @param modifier Modifier to be applied to the layout.
     *
     */
    @Composable
    fun EnterJournalEntry(modifier: Modifier = Modifier.Companion) {
        var title: String by remember { mutableStateOf("") }
        var content: String by remember { mutableStateOf("") }

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Text(
                text = "Enter Journal Here",
                textAlign = TextAlign.Companion.Center,
                fontWeight = FontWeight.Companion.Bold,
                fontSize = 24.sp,
                modifier = Modifier.Companion
                    .padding(8.dp)
            )

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title", fontWeight = FontWeight.Companion.Bold) },
                maxLines = 2,
                textStyle = TextStyle(color = Color.Companion.Magenta, fontWeight = FontWeight.Companion.SemiBold),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Companion.Password,
                    imeAction = ImeAction.Companion.Next,
                ),
                /**
                 * @param keyboardActions.onNext Called when the next action is performed on the keyboard.
                 */
                keyboardActions = KeyboardActions(

                ),
                modifier = Modifier.Companion
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.Companion.height(8.dp))

            OutlinedTextField(
                value = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(Date()),
                onValueChange = {},
                label = { Text("Date", fontWeight = FontWeight.Companion.Bold) },
                modifier = Modifier.Companion
                    .fillMaxWidth(),
                readOnly = true
            )

            Spacer(modifier = Modifier.Companion.height(16.dp))

            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Content", fontWeight = FontWeight.Companion.Bold) },
                modifier = Modifier.Companion
                    .height(300.dp)
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            Button(
                onClick = {
                    if (title.isNotEmpty() && content.isNotEmpty()) {
                        journalViewModel
                            .addEntry(
                                title = title,
                                content = content,
                            )
                        title = ""
                        content = ""

                        val intent = Intent(this@MainActivity, JournalListActivity::class.java)
                        startActivity(intent)

                        Toast.makeText(this@MainActivity, "Journal Entry Saved", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@MainActivity, "Please make entry in Title and Context Fields.", Toast.LENGTH_LONG).show()
                    }
                },
                modifier = Modifier.Companion
                    .align(Alignment.Companion.CenterHorizontally),
            ) {
                Text(
                    text = "Save Entry"
                )
            }
        }
    }

    /**
     * Preview function for the EnterJournalEntry composable.
     *
     * @param modifier Modifier to be applied to the preview.
     *
     */
    @Preview(showBackground = true)
    @Composable
    fun EnterJournalEntryPreview(modifier: Modifier = Modifier.Companion) {
        DailyPractice2Theme {
            EnterJournalEntry()
        }
    }
}
