package com.example.dailypractice2.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "journal_entries")
data class JournalEntryEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val date : String,
    )