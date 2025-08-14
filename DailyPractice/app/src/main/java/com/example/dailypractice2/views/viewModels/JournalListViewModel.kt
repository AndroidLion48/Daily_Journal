package com.example.dailypractice2.views.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.dailypractice2.data.JournalRepositories
import com.example.dailypractice2.models.JournalEntryModel
import com.example.dailypractice2.models.toDomainModel
import kotlinx.coroutines.launch


/**
 * ViewModel for managing the list of journal entries.
 *
 * @property repository The repository for accessing journal entry data.
 */
class JournalListViewModel(private val repository: JournalRepositories) : ViewModel() {

    /**
     * A list of journal entries.
     * The list is observed for changes and updates the UI accordingly.
     * It can only be set privately within this ViewModel.
     */
    var journalEntries by mutableStateOf<List<JournalEntryModel>>(emptyList())
        private set

    /**
     * The currently selected journal entry.
     * This can be null if no entry is selected.
     */
    var selectedEntry by mutableStateOf<JournalEntryModel?>(null)

    init {
        loadJournalEntries()
    }
    /** Loads journal entries from the repository and updates the [journalEntries] state. */
    fun loadJournalEntries() {
        viewModelScope.launch {
            val entriesList = repository.getAllEntities()
            journalEntries = entriesList.map { it.toDomainModel() }
        }
    }
}

/**
 * Factory for creating instances of [JournalListViewModel].
 *
 * @property repository The repository to be injected into the ViewModel.
 */
class JournalListViewModelFactory(
    private val repository: JournalRepositories
) : ViewModelProvider.Factory {
    /** Creates a new instance of the [JournalListViewModel]. */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return JournalListViewModel(repository) as T
    }
}
