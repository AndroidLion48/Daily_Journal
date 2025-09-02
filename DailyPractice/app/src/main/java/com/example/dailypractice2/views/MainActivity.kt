package com.example.dailypractice2.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.example.dailypractice2.data.DailyJournalRepositoryImpl
import com.example.dailypractice2.data.local.JournalDatabase
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
        val repository =
            DailyJournalRepositoryImpl(journalDatabase = JournalDatabase.getInstance(this))

        val factory = JournalViewModelFactory(repository)
        ViewModelProvider(this, factory)[JournalViewModel::class.java]
    }

    var currentScreen = 0

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
                    // Composable goes here
                    InitialJournalScreen()
                }
            }
        }
    }

    @Composable
    private fun InitialJournalScreen() {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                modifier = Modifier,
                textAlign = TextAlign.Center,
                text = "MY DAILY JOURNAL APP",
                fontSize = 24.sp,
            )

            ContentsScreen()

            ButtonsScreen()
        }
    }

    @Composable
    fun ButtonsScreen() {
        // Added a save button
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Button(
                modifier = Modifier,
                onClick = {
                    currentScreen = 0
                },
            ) {
                Text(text = "New Entry")
            }

            Button(
                modifier = Modifier,
                onClick = {
                    currentScreen = 1
                },
            ) {
                Text(text = "List")
            }
        }
    }

    @Composable
    fun ContentsScreen() {
        Column() {
            if (currentScreen == 0) {
                EnterJournalEntry()
            } else if (currentScreen == 1) {
                Text(text = "List")
                JournalListScreen()
            }
        }
    }

    @Composable
    fun JournalListScreen() {
        
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
                textStyle = TextStyle(
                    color = Color.Companion.Magenta,
                    fontWeight = FontWeight.Companion.SemiBold
                ),
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

    @Composable
    @Preview
    private fun InitialJournalScreenPreview() {
        InitialJournalScreen()

    }
}