package com.example.dailypractice2.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dailypractice2.data.local.models.JournalEntryEntity

@Dao
interface JournalDao {


    @Query("SELECT * FROM journal_entries")
    suspend fun getAllEntries(): List<JournalEntryEntity>

    @Query("SELECT * FROM journal_entries WHERE id = :id")
    suspend fun getEntryById(id: Int): JournalEntryEntity?

    @Insert
    suspend fun insertEntry(entry: JournalEntryEntity)

    @Query("DELETE FROM journal_entries WHERE id = :id")
    suspend fun deleteEntry(id: Int)
}