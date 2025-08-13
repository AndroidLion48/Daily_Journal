package com.example.dailypractice2.views.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.dailypractice2.data.JournalRepositories
import com.example.dailypractice2.data.entity.JournalEntryEntity
import com.example.dailypractice2.models.JournalEntryModel
import com.example.dailypractice2.models.toDomainModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Created by Clarence E Moore on 2025-04-20.
 *
 * Description:
 *
 *           : is of type
 *           = is to return
 */
class JournalViewModel(private val repository: JournalRepositories) : ViewModel() {

    var journalEntries by mutableStateOf<List<JournalEntryModel>>(emptyList())
        private set

    var selectedEntry by mutableStateOf<JournalEntryModel?>(null)
        private set

    private val _dailyEntries = mutableStateListOf<JournalEntryModel>()
    val dailyEntries: List<JournalEntryModel> = _dailyEntries

    init {
        loadJournalEntries()
    }

    private fun loadJournalEntries() {
        viewModelScope.launch {
            val entitiesList = repository.getAllEntities()
            journalEntries = entitiesList.map { it.toDomainModel() }
        }
    }

    private fun loadEntryById(id: Long) {
        viewModelScope.launch {
            val entity = repository.getEntityById(id)
            selectedEntry = entity.toDomainModel()
        }
    }

    fun addEntry(title: String, content: String) {
        //                  = is to return
        val currentDate = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(Date())

        viewModelScope.launch {
            repository.upsertEntity(
                entity = JournalEntryEntity(
                    title = title,
                    entry = content,
                    entryDate = currentDate,
                    id = 0,
                )
            )
        }
    }

    fun clearEntries() {
        viewModelScope.launch {
            var entitiesList = repository.getAllEntities()
            entitiesList = emptyList()
        }
    }
}

class JournalViewModelFactory(
    private val repository: JournalRepositories
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create (modelClass: Class<T>): T {
        return JournalViewModel(repository) as T
    }
}
