package com.example.dailypractice2.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dailypractice2.data.dao.JournalDao
import com.example.dailypractice2.data.entity.JournalEntryEntity

/**
 * Room database for storing journal entries.
 *
 * This class is annotated with `@Database` to define the entities, version, and export schema.
 * It extends [RoomDatabase] and provides an abstract method to access the [JournalDao].
 * It also includes a companion object with a singleton pattern to get the database instance.
 */

@Database(entities = [JournalEntryEntity::class], version = 1, exportSchema = false)
abstract class JournalDatabase : RoomDatabase() {

    /**
     * Abstract method to get the Data Access Object (DAO) for journal entries.
     * @return The [JournalDao] instance.
     */
    abstract fun journalDao(): JournalDao

    companion object {
        @Volatile
        private var instance: JournalDatabase? = null
        /**
         * Gets the singleton instance of the [JournalDatabase].
         * If the instance is null, it creates a new database instance.
         * @param context The application context.
         * @return The singleton [JournalDatabase] instance.
         */
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
