package com.example.dailypractice2.views.viewModels

import android.util.Log
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
import com.google.firebase.Firebase
import com.google.firebase.database.database
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


/**
 * ViewModel for managing journal entries.
 *
 * This ViewModel interacts with the [JournalRepositories] to perform CRUD operations
 * on journal entries and exposes the data to the UI.
 *
 * @property repository The repository for accessing journal entry data.
 */
class JournalViewModel(private val repository: JournalRepositories) : ViewModel() {

    /**
     * A list of all journal entries.
     *
     * This property is observed by the UI and updates automatically when the data changes.
     */
    var journalEntries by mutableStateOf<List<JournalEntryModel>>(emptyList())
        private set

    /**
     * The currently selected journal entry.
     *
     * This property is used to display the details of a specific entry in the UI.
     */
    var selectedEntry by mutableStateOf<JournalEntryModel?>(null)
        private set

    private val _dailyEntries = mutableStateListOf<JournalEntryModel>()
    val dailyEntries: List<JournalEntryModel> = _dailyEntries

    init {
        loadJournalEntries()
    }

    /**
     * Loads all journal entries from the repository.
     *
     * This function is called when the ViewModel is initialized.
     */
    private fun loadJournalEntries() {
        viewModelScope.launch {
            val entitiesList = repository.getAllEntities()
            journalEntries = entitiesList.map { it.toDomainModel() }
        }
    }
    /**
     * Loads a specific journal entry from the repository by its ID.
     *
     * @param id The ID of the journal entry to load.
     */
    private fun loadEntryById(id: Long) {
        viewModelScope.launch {
            val entity = repository.getEntityById(id)
            selectedEntry = entity.toDomainModel()
        }
    }
    /**
     * Adds a new journal entry to the repository.
     *
     * @param title The title of the new journal entry.
     * @param content The content of the new journal entry.
     */
    fun addEntry(title: String, content: String) {
        val currentDate = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(Date())
        viewModelScope.launch {
                val entity = JournalEntryEntity(
                    title = title,
                    entry = content,
                    entryDate = currentDate,
                    id = 0,
                )
            repository.upsertEntity(entity)
            // Sync journal entry to Firebase Realtime Database
            syncJournalEntryToFirebase(entity)
        }
    }

    /**
     * Syncs a single journal entry to Firebase Realtime Database.
     *
     * @param entity The journal entry entity to be synced.
     */
    private fun syncJournalEntryToFirebase(entity: JournalEntryEntity){

        // Firebase Realtime Database references
        val database = Firebase.database
        val journalRef = database.getReference("journal_entries")
        val entryMap = mapOf(
            "title" to entity.title,
            "content" to entity.entry,
            "date" to entity.entryDate,
            "timestamp" to System.currentTimeMillis(),
        )

        journalRef.push().setValue(entryMap)
            .addOnSuccessListener {
                Log.d("Firebase", "Journal entry synced to Firebase successfully")
            }
            .addOnFailureListener { exception ->
                Log.e("Firebase", "Failed to sync journal entry to Firebase", exception)
            }
    }

    /**
     * Syncs all journal entries from the local repository to Firebase.
     *
     * This function retrieves all entries from the local repository and
     * uploads each one to Firebase.
     */
    fun syncAllJournalEntriesToFirebase(){
        viewModelScope.launch {
            val allEntities = repository.getAllEntities()
            allEntities.forEach { entity ->
                syncJournalEntryToFirebase(entity)
            }
        }
    }

    /**
     * Clears all journal entries from the repository.
     * (Currently, this function only clears the local list and does not interact with the repository)
     */
    fun clearEntries() {
        viewModelScope.launch {
            var entitiesList = repository.getAllEntities()
            entitiesList = emptyList()
        }
    }
}

/**
 * Factory for creating instances of [JournalViewModel].
 *
 * @property repository The repository to be injected into the ViewModel.
 */
class JournalViewModelFactory(
    private val repository: JournalRepositories
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create (modelClass: Class<T>): T {
        return JournalViewModel(repository) as T
    }
}
