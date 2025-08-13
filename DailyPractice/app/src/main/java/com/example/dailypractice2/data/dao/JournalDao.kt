package com.example.dailypractice2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.dailypractice2.data.entity.JournalEntryEntity

/**
 * Created by Clarence E Moore on 2025-05-26.
 *
 * Description:
 *
 *
 */

@Dao
interface JournalDao {

    @Upsert
    suspend fun upsertEntities(entities: List<JournalEntryEntity>)

    @Upsert
    suspend fun upsertEntity(entity: JournalEntryEntity)

    @Query("SELECT * FROM journal_entries")
    suspend fun getAllEntities(): List<JournalEntryEntity>

    @Query("SELECT * FROM journal_entries WHERE id = :id")
    suspend fun getEntityById(id: Long): JournalEntryEntity

    @Delete
    suspend fun deleteEntity(entity: JournalEntryEntity)
}
