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
 * Created by Clarence E Moore on 2025-05-26.
 *
 * Description:
 *
 * JournalListActivity ViewModel
 *
 *
 */

class JournalListViewModel(private val repository: JournalRepositories) : ViewModel() {

    var journalEntries by mutableStateOf<List<JournalEntryModel>>(emptyList())
        private set
    var selectedEntry by mutableStateOf<JournalEntryModel?>(null)

    init {
        loadJournalEntries()
    }

    fun loadJournalEntries() {
        viewModelScope.launch {
            val entriesList = repository.getAllEntities()
            journalEntries = entriesList.map { it.toDomainModel() }
        }
    }
}

class JournalListViewModelFactory(
    private val repository: JournalRepositories
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return JournalListViewModel(repository) as T
    }
}
