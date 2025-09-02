package com.example.dailypractice2.data

import com.example.dailypractice2.data.local.JournalDao
import com.example.dailypractice2.data.local.JournalDatabase
import com.example.dailypractice2.data.local.models.JournalEntryEntity

class DailyJournalRepositoryImpl(journalDatabase: JournalDatabase) : DailyJournalRepository {

    val journalDao: JournalDao = journalDatabase.journalDao()

    override suspend fun getAllEntries(): List<JournalEntryEntity> {
        return journalDao.getAllEntries()
    }

    override suspend fun getEntryById(id: Int): JournalEntryEntity? {
        return journalDao.getEntryById(id)
    }

    override suspend fun insertEntry(entry: JournalEntryEntity) {
        return journalDao.insertEntry(entry)
    }

    override suspend fun deleteEntry(id: Int) {
        return journalDao.deleteEntry(id)
    }
}