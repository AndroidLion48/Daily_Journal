package com.example.dailypractice2.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.dailypractice2.data.JournalRepositoriesImpl
import com.example.dailypractice2.data.database.JournalDatabase
import com.example.dailypractice2.views.ui.JournalEntryScreen
import com.example.dailypractice2.views.ui.theme.DailyPractice2Theme
import com.example.dailypractice2.views.viewModels.JournalViewModel
import com.example.dailypractice2.views.viewModels.JournalViewModelFactory


/**
 * The main activity of the application, responsible for displaying the UI to enter and save journal entries.
 *
 * This activity uses Jetpack Compose for building the UI and ViewModel for managing UI-related data.
 * It initializes the [JournalViewModel] which is responsible for handling the business logic
 * related to journal entries. The UI is built using the [JournalEntryScreen] composable function.
 */
class MainActivity : ComponentActivity() {

    /**
     * Lazily initialized instance of [JournalViewModel].
     *
     * This ViewModel is responsible for managing the data and business logic
     * related to journal entries. It is created using a [JournalViewModelFactory]
     * which in turn uses a [JournalRepositoriesImpl] to interact with the
     * underlying data source ([JournalDatabase]).
     */
    private val journalViewModel: JournalViewModel by lazy {
        val repository = JournalRepositoriesImpl(journalDatabase = JournalDatabase.getInstance(this))
        val factory = JournalViewModelFactory(repository)
        ViewModelProvider(this, factory)[JournalViewModel::class.java]
    }

    /**
     * Called when the activity is first created.
     *
     * This method is responsible for setting up the activity, including:
     * - Enabling edge-to-edge display.
     * - Setting the content view using Jetpack Compose.
     * - Initializing the UI by calling the [JournalEntryScreen] composable,
     *   passing in the [journalViewModel].
     */
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
                    JournalEntryScreen(journalViewModel = journalViewModel)
                }
            }
        }
    }
}
