package com.example.dailypractice2.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dailypractice2.data.dao.JournalDao
import com.example.dailypractice2.data.entity.JournalEntryEntity

/**
 * Created by Clarence E Moore on 2025-05-26.
 *
 * Description:
 *
 *
 */

@Database(entities = [JournalEntryEntity::class], version = 1, exportSchema = false)
abstract class JournalDatabase : RoomDatabase() {

    abstract fun journalDao(): JournalDao

    companion object {
        @Volatile
        private var instance: JournalDatabase? = null

        fun getInstance(context: Context): JournalDatabase =
            instance ?: synchronized(this) {
                // if instance is null make a new database
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JournalDatabase::class.java,
                    "journal_database",
                ).build()
                Companion.instance = instance
                instance
            }
    }
}
