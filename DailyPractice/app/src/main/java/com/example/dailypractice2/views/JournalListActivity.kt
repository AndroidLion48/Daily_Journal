@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.example.dailypractice2.views

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dailypractice2.data.JournalRepositoriesImpl
import com.example.dailypractice2.data.database.JournalDatabase
import com.example.dailypractice2.models.JournalEntryModel
import com.example.dailypractice2.views.theme.DailyPractice2Theme
import com.example.dailypractice2.views.viewModels.JournalListViewModel
import com.example.dailypractice2.views.viewModels.JournalListViewModelFactory

/**
 * Created by Clarence E Moore on 2025-05-26.
 *
 * Description:
 *
 * JournalListActivity
 *
 * Practice:
 * 1. Add another Activity:
 * Details page for a single journal entry
 * activated by clicking on a card.
 * 1. Accomplished using AnimatedVisibilityScope transitions.
 *
 *
 *
 */
class JournalListActivity : ComponentActivity() {
    private val journalListViewModel: JournalListViewModel by lazy {
        val repository = JournalRepositoriesImpl(journalDatabase = JournalDatabase.getInstance(this))
        val factory = JournalListViewModelFactory(repository)
        ViewModelProvider(this, factory)[JournalListViewModel::class.java]
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

                    SharedTransitionLayout {
                        val navController = rememberNavController()

                        NavHost(
                            navController = navController,
                            startDestination = "journal_list_screen",
                        ) {
                            composable("journal_list_screen") {
                                JournalListScreen(
                                    onItemClick = { selectedCardIndex ->
                                        navController.navigate("details_screen/$selectedCardIndex")
                                    },
                                    animatedVisibilityScope = this,
                                )
                            }

                            composable(
                                route = "details_screen/{cardIndex}",
                                arguments = listOf(navArgument("cardIndex") { type = NavType.IntType })
                            ) { backStackEntry ->

                                val cardIndex = backStackEntry.arguments?.getInt("cardIndex")
                                val selectedCard = cardIndex?.let { journalListViewModel.journalEntries[it] }

                                DetailsScreen(
                                    modifier = Modifier.Companion.fillMaxSize(),
                                    selectedEntry = selectedCard ?: JournalEntryModel(title = "No Entry", content = "No Content", date = "No Date", id = 0),
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

    @Composable
    fun SharedTransitionScope.DetailsScreen(
        modifier: Modifier,
        selectedEntry: JournalEntryModel,
        onItemClick: (index: Int) -> Unit,
        animatedVisibilityScope: AnimatedVisibilityScope,
    ) {

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp, 38.dp, 16.dp, 16.dp)
                .clickable { onItemClick(selectedEntry.id) },
        ) {

            Text(
                text = selectedEntry.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Companion.Bold,
                modifier = Modifier
                    .Companion.padding(bottom = 8.dp)
                    .sharedElement(
                        state = rememberSharedContentState(key = "journal_entry_${selectedEntry.title}"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { _, _ ->
                            tween(durationMillis = 1000)
                        }
                    ),
            )
            Text(
                text = selectedEntry.date,
                fontSize = 16.sp,
                color = Color.Companion.Gray,
                modifier = Modifier
                    .Companion.padding(bottom = 16.dp)
                    .sharedElement(
                    state = rememberSharedContentState(key = "journal_entry_${selectedEntry.date}"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = { _, _ ->
                        tween(durationMillis = 1000)
                    }
                ),
            )
            Text(
                text = selectedEntry.content,
                fontSize = 18.sp,
                modifier = Modifier
                    .Companion.padding(bottom = 16.dp)
                    .sharedElement(
                        state = rememberSharedContentState(key = "journal_entry_${selectedEntry.content}"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { _, _ ->
                            tween(durationMillis = 1000)
                        }
                    ),
            )
        }
    }


    @Composable
    fun SharedTransitionScope.JournalListScreen(
        modifier: Modifier = Modifier,
        onItemClick: (index: Int) -> Unit,
        animatedVisibilityScope: AnimatedVisibilityScope,
    ) {
        val listOfEntries = journalListViewModel.journalEntries
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Button(
                    onClick = {
                        val intent = Intent(this@JournalListActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                ) {
                    Text("Add Entry")
                }
            }
            Row(
                modifier = Modifier
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(listOfEntries.size) { index ->
                        JournalEntryCard(
                            Modifier
                                .sharedElement(
                                    state = rememberSharedContentState(key = "journal_entry_$index"),
                                    animatedVisibilityScope = animatedVisibilityScope,
                                    boundsTransform = { _, _ ->
                                        tween(durationMillis = 1000)
                                    }
                                ),
                            listOfEntries[index],
                            onItemClick = { onItemClick(index) }
                        )
                        Spacer(modifier = Modifier.Companion.height(16.dp))
                    }
                }
            }
        }
    }

    /**
     * Displays a card for a single journal entry.
     *
     * @param entry The JournalEntryModel to display.
     */
    @Composable
    fun JournalEntryCard(modifier: Modifier = Modifier, entry: JournalEntryModel, onItemClick: (index: Int) -> Unit) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .clickable { onItemClick(entry.id) },
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.Companion.padding(16.dp)) {
                Text(entry.title, fontSize = 18.sp, fontWeight = FontWeight.Companion.SemiBold)
                Text(entry.date, fontSize = 14.sp, color = Color.Companion.Gray)
                Spacer(modifier = Modifier.Companion.height(8.dp))
                Text(entry.content, fontSize = 16.sp, fontWeight = FontWeight.Companion.SemiBold)
            }
        }
    }
}
