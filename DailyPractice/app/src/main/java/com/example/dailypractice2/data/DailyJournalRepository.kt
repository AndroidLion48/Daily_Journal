package com.example.dailypractice2.data

import com.example.dailypractice2.data.local.models.JournalEntryEntity

interface DailyJournalRepository {

    suspend fun getAllEntries(): List<JournalEntryEntity>

    suspend fun getEntryById(id: Int): JournalEntryEntity?

    suspend fun insertEntry(entry: JournalEntryEntity)

    suspend fun deleteEntry(id: Int)

}