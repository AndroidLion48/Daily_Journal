package com.example.dailypractice2.data.firebase

import com.example.dailypractice2.data.entity.JournalEntryEntity

/**
 * Created by Clarence E Moore on 2025-09-13.
 *
 * Description:
 *
 *
 */

/**
 * Repository interface for Firebase journal operations
 * Abstracts data operations from ViewModels
 */

interface FirebaseJournalRepository {
    suspend fun saveJournalEntry(entity: JournalEntryEntity): String
    suspend fun getAllJournalEntries(): List<JournalEntryEntity>
    suspend fun getJournalEntryById(id: String): JournalEntryEntity?
    suspend fun deleteJournalEntry(id: String)
}

/**
 * Firebase repository implementation following repository pattern
 * Acts as abstraction layer between ViewModel and data source
 */
class FirebaseJournalRepositoryImpl(
    private val dataSource: FirebaseJournalDataSource = FirebaseJournalDataSourceImpl()
): FirebaseJournalRepository {
    /**
     * Delegates journal entry saving to data source
     */
    override suspend fun saveJournalEntry(entity: JournalEntryEntity): String {
        return dataSource.saveJournalEntry(entity)
    }

    /**
     * Delegates fetching all entries to data source
     */
    override suspend fun getAllJournalEntries(): List<JournalEntryEntity> {
        return dataSource.getAllJournalEntries()
    }

    /**
     * Delegates fetching entry by ID to data source
     */
    override suspend fun getJournalEntryById(id: String): JournalEntryEntity? {
        return dataSource.getJournalEntryById(id)
    }

    /**
     * Delegates entry deletion to data source
     */
    override suspend fun deleteJournalEntry(id: String) {
        dataSource.deleteJournalEntry(id)
    }
}
