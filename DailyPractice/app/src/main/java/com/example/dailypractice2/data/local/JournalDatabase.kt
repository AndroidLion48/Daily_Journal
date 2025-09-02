package com.example.dailypractice2.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dailypractice2.data.local.models.JournalEntryEntity

@Database(
    entities = [JournalEntryEntity::class],
    version = 1,
)
abstract class JournalDatabase(): RoomDatabase() {
    abstract fun journalDao(): JournalDao

    companion object{
        @Volatile
        private var journalDatabaseInstance: JournalDatabase? = null

        fun getInstance(context: Context): JournalDatabase {
            return journalDatabaseInstance ?: synchronized(this) {
                val newDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    JournalDatabase::class.java,
                    "daily_journal_database",
                ).build()

                Companion.journalDatabaseInstance = newDatabase
                newDatabase
            }

        }
    }


}