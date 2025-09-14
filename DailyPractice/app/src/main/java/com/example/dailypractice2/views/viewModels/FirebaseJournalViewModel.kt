package com.example.dailypractice2.views.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.dailypractice2.data.entity.JournalEntryEntity
import com.example.dailypractice2.data.firebase.FirebaseJournalRepository
import com.example.dailypractice2.data.firebase.FirebaseJournalRepositoryImpl
import com.example.dailypractice2.models.JournalEntryModel
import com.example.dailypractice2.models.toDomainModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


/**
 * ViewModel for Firebase journal operations
 * Manages UI state and coordinates with repository
 */
class FirebaseJournalViewModel (
    private val repository: FirebaseJournalRepository = FirebaseJournalRepositoryImpl()
) : ViewModel() {

    var journalEntriesList by mutableStateOf<List<JournalEntryModel>>(emptyList())
        private set

    var selectedEntry by mutableStateOf<JournalEntryModel?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        loadJournalEntries()
    }

    /**
     * Loads all journal entries from Firebase
     */
    private fun loadJournalEntries() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val allEntities = repository.getAllJournalEntries()
                // Convert entities to domain models for UI
                journalEntriesList = allEntities.map { it.toDomainModel()}
                Log.d("FirebaseJournalViewModel", "Loaded ${journalEntriesList.size} journal entries from Firebase")
            } catch (e: Exception) {
                errorMessage = "Failed to load journal entries: ${e.message}"
                Log.e("FirebaseJournalViewModel", "Error loading journal entries", e)
            } finally {
                isLoading = false
            }
        }
    }

    /**
     * Saves a new journal entry to Firebase and refreshes the list.
     *
     * @param title The title of the journal entry.
     * @param content The content of the journal entry.
     */

    fun saveJournalEntry(title: String, content: String) {
        val currentDate = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(Date())
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val entity = JournalEntryEntity(
                    id = 0, // ID will be generated Firebase
                    title = title,
                    entry = content,
                    entryDate = currentDate
                )
                repository.saveJournalEntry(entity)
                // Refresh the list after saving
                loadJournalEntries()
                Log.d("FirebaseJournalViewModel", "Saved new journal entry to Firebase")
            } catch (e: Exception){
                errorMessage = "Failed to save journal entry: ${e.message}"
                Log.e("FirebaseJournalViewModel", "Error saving journal entry", e)
            } finally {
                isLoading = false
            }
        }
    }

    /**
     * Fetches a journal entry by its ID and updates the [selectedEntry] state.
     *
     * @param id The ID of the journal entry to fetch.
     */
    fun getJournalEntryById(id: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try{
                val entity = repository.getJournalEntryById(id)
                // Convert entity to domain model for UI
                selectedEntry = entity?.toDomainModel()
            } catch (e: Exception) {
                errorMessage = "Failed to load journal entry: ${e.message}"
                Log.e("FirebaseJournalViewModel", "Error loading journal entry by ID", e)
            } finally {
                isLoading = false
            }
        }
    }

    /**
     * Deletes a journal entry by its ID and refreshes the list.
     *
     * @param id The ID of the journal entry to delete.
     */
    fun deleteJournalEntry(id: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                repository.deleteJournalEntry(id)
                // Refresh the list after deletion
                loadJournalEntries()
                Log.d("FirebaseJournalViewModel", "Deleted journal entry with ID: $id from Firebase")
            } catch (e: Exception) {
                errorMessage = "Failed to delete journal entry: ${e.message}"
                Log.e("FirebaseJournalViewModel", "Error deleting journal entry", e)
            } finally {
                isLoading = false
            }
        }
    }

    /**
     * Clears any error message currently set in the UI state.
     */
    fun clearError() {
        errorMessage = null
    }

    /**
     * Clears the currently selected journal entry.
     */
    fun clearSelectedEntry() {
        selectedEntry = null
    }

    /**
     * Factory for creating instances of [FirebaseJournalViewModel].
     *
     * @property repository The repository to be injected into the ViewModel.
     */
    class FirebaseJournalViewModelFactory(
        private val repository: FirebaseJournalRepository = FirebaseJournalRepositoryImpl()
    ) : ViewModelProvider.Factory {
        override fun <T: ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FirebaseJournalViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FirebaseJournalViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
