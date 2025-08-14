package com.example.dailypractice2.data

import com.example.dailypractice2.data.entity.JournalEntryEntity

/**
 * Created by Clarence E Moore on 2025-05-26.
 *
 * Interface for accessing Journal Entry data.
 * This interface defines the contract for data operations related to Journal Entries.
 * Implementations of this interface will handle the actual data storage and retrieval.
 *
 */

interface JournalRepositories {

    /**
     * Inserts or updates a list of journal entries.
     * If an entry already exists, it will be updated; otherwise, it will be inserted.
     * @param entities The list of [JournalEntryEntity] to be upserted.
     */
    suspend fun upsertEntities(entities: List<JournalEntryEntity>)

    /**
     * Inserts or updates a single journal entry.
     * If the entry already exists, it will be updated; otherwise, it will be inserted.
     * @param entity The [JournalEntryEntity] to be upserted.
     */
    suspend fun upsertEntity(entity: JournalEntryEntity)

    /**
     * Retrieves all journal entries.
     * @return A list of all [JournalEntryEntity] objects.
     */
    suspend fun getAllEntities(): List<JournalEntryEntity>

    /**
     * Retrieves a specific journal entry by its ID.
     * @param id The unique identifier of the journal entry.
     * @return The [JournalEntryEntity] with the specified ID, or null if not found.
     */
    suspend fun getEntityById(id: Long): JournalEntryEntity

    /**
     * Deletes a specific journal entry.
     * @param entity The [JournalEntryEntity] to be deleted.
     */
    suspend fun deleteEntity(entity: JournalEntryEntity)
}
