package com.example.dailypractice2.data

import com.example.dailypractice2.data.entity.JournalEntryEntity

/**
 * Created by Clarence E Moore on 2025-05-26.
 *
 * Description:
 *
 *
 */
interface JournalRepositories {

    suspend fun upsertEntities(entities: List<JournalEntryEntity>)

    suspend fun upsertEntity(entity: JournalEntryEntity)

    suspend fun getAllEntities(): List<JournalEntryEntity>

    suspend fun getEntityById(id: Long): JournalEntryEntity

    suspend fun deleteEntity(entity: JournalEntryEntity)
}
