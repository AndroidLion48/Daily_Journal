@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.example.dailypractice2.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dailypractice2.data.JournalRepositoriesImpl
import com.example.dailypractice2.data.database.JournalDatabase
import com.example.dailypractice2.models.JournalEntryModel
import com.example.dailypractice2.views.ui.DetailsScreen
import com.example.dailypractice2.views.ui.JournalListScreen
import com.example.dailypractice2.views.ui.theme.DailyPractice2Theme
import com.example.dailypractice2.views.viewModels.JournalListViewModel
import com.example.dailypractice2.views.viewModels.JournalListViewModelFactory

/**
 * Activity responsible for displaying a list of journal entries and navigating to their details.
 * It utilizes Jetpack Compose for the UI and a ViewModel to manage journal data.
 */

/**
 * Main activity for the Journal application.
 * This activity is responsible for setting up the UI using Jetpack Compose and
 * managing navigation between different screens (Journal List, Journal Details, Add Entry).
 * It uses a [JournalListViewModel] to interact with the data layer.
 */
class JournalListActivity : ComponentActivity() {
    /** Lazily initializes the [JournalListViewModel] using a [JournalListViewModelFactory]. */
    private val journalListViewModel: JournalListViewModel by lazy {
        // Obtain an instance of the repository implementation.
        val repository = JournalRepositoriesImpl(journalDatabase = JournalDatabase.getInstance(this))
        val factory = JournalListViewModelFactory(repository)
        // Create and return the ViewModel instance.
        ViewModelProvider(this, factory)[JournalListViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DailyPractice2Theme {

                SharedTransitionLayout {
                    // Create a NavController to handle navigation between composables.
                    val navController = rememberNavController()

                    // Define the navigation graph using NavHost.
                    NavHost(
                        navController = navController,
                        startDestination = "journal_list_screen",
                    ) {
                        // Define the composable for the journal list screen.
                        composable("journal_list_screen") {
                            // Display the JournalListScreen, passing the list of entries and navigation callbacks.
                            JournalListScreen(
                                journalEntriesList = journalListViewModel.journalEntries,
                                animatedVisibilityScope = this,
                                // Navigate to the details screen when an item is clicked.
                                onItemClick = { selectedEntry ->
                                    navController.navigate("journal_details_screen/$selectedEntry")
                                },
                                onAddEntryClick = {
                                    navController.navigate("add_entry_screen")
                                },
                            )
                        }

                        // Define the composable for the journal details screen.
                        composable(
                            route = "journal_details_screen/{entryId}",
                            // Define an argument for the entryId, which is an integer.
                            arguments = listOf(navArgument("entryId") { type = NavType.IntType }),
                        ) { backStackEntry ->
                            // Retrieve the entryId from the navigation arguments.
                            val entryId = backStackEntry.arguments?.getInt("entryId")
                            // Find the corresponding journal entry from the ViewModel.
                            val selectedCard = entryId?.let { journalListViewModel.journalEntries.find { entry -> entry.id == it} }

                            DetailsScreen(
                                modifier = Modifier.Companion.fillMaxSize(),
                                selectedEntry = selectedCard ?: JournalEntryModel(title = "No Entry", content = "No Content", date = "No Date", id = 0),
                                // Navigate back to the journal list screen when the item is clicked (could be a back button).
                                onItemClick = {
                                    navController.navigate("journal_list_screen")
                                },
                                animatedVisibilityScope = this,
                            )
                        }
                    }
                }
            }
        }
    }
}
