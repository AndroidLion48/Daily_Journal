package com.example.dailypractice2.views.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dailypractice2.data.DailyJournalRepository


/**
 * Created by Clarence E Moore on 2025-04-20.
 *
 * Description:
 *
 *           : is of type
 *           = is to return
 */
class JournalViewModel(private val repository: DailyJournalRepository) : ViewModel() {

//    var journalEntries by mutableStateOf<List<JournalEntryModel>>(emptyList())
//        private set

    init {

    }
}

class JournalViewModelFactory(
    private val repository: DailyJournalRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create (modelClass: Class<T>): T {
        return JournalViewModel(repository) as T
    }
}