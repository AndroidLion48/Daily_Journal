package com.example.dailypractice2.data

import com.example.dailypractice2.data.database.JournalDatabase
import com.example.dailypractice2.data.entity.JournalEntryEntity

/**
 * Created by Clarence E Moore on 2025-05-26.
 *
 * Description:
 *
 *
 */


class JournalRepositoriesImpl(private val journalDatabase: JournalDatabase) : JournalRepositories {

    override suspend fun upsertEntities(entities: List<JournalEntryEntity>) {
        journalDatabase.journalDao().upsertEntities(entities)
    }

    override suspend fun upsertEntity(entity: JournalEntryEntity) {
        journalDatabase.journalDao().upsertEntity(entity)
    }

    override suspend fun getAllEntities(): List<JournalEntryEntity> {
        return journalDatabase.journalDao().getAllEntities()
    }

    override suspend fun getEntityById(id: Long): JournalEntryEntity {
        return journalDatabase.journalDao().getEntityById(id)
    }

    override suspend fun deleteEntity(entity: JournalEntryEntity) {
        journalDatabase.journalDao().deleteEntity(entity)
    }
}
