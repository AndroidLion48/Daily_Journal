package com.example.dailypractice2.data

import com.example.dailypractice2.data.database.JournalDatabase
import com.example.dailypractice2.data.entity.JournalEntryEntity

/**
 * Implementation of [JournalRepositories] that interacts with the [JournalDatabase].
 *
 * @property journalDatabase The database instance used for data operations.
 */
class JournalRepositoriesImpl(private val journalDatabase: JournalDatabase) : JournalRepositories {

    /**
     * Inserts or updates a list of journal entries in the database.
     *
     * @param entities The list of [JournalEntryEntity] to be upserted.
     */
    override suspend fun upsertEntities(entities: List<JournalEntryEntity>) {
        journalDatabase.journalDao().upsertEntities(entities)
    }

    /**
     * Inserts or updates a single journal entry in the database.
     *
     * @param entity The [JournalEntryEntity] to be upserted.
     */
    override suspend fun upsertEntity(entity: JournalEntryEntity) {
        journalDatabase.journalDao().upsertEntity(entity)
    }

    /**
     * Retrieves all journal entries from the database.
     *
     * @return A list of all [JournalEntryEntity] in the database.
     */
    override suspend fun getAllEntities(): List<JournalEntryEntity> {
        return journalDatabase.journalDao().getAllEntities()
    }

    /**
     * Retrieves a journal entry by its ID from the database.
     *
     * @param id The ID of the journal entry to retrieve.
     * @return The [JournalEntryEntity] with the specified ID.
     */
    override suspend fun getEntityById(id: Long): JournalEntryEntity {
        return journalDatabase.journalDao().getEntityById(id)
    }

    /**
     * Deletes a journal entry from the database.
     *
     * @param entity The [JournalEntryEntity] to be deleted.
     */
    override suspend fun deleteEntity(entity: JournalEntryEntity) {
        journalDatabase.journalDao().deleteEntity(entity)
    }
}
