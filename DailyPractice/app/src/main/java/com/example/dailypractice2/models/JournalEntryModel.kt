package com.example.dailypractice2.models

import com.example.dailypractice2.data.entity.JournalEntryEntity

/**
 * Created by Clarence E Moore on 2025-04-20.
 *
 * Description:
 *
 *
 */
data class JournalEntryModel(
    val title : String,
    val content: String,
    val date: String,
    val id: Int
)

fun JournalEntryEntity.toDomainModel(): JournalEntryModel {
    return JournalEntryModel(
        title = title,
        content = entry,
        date = entryDate,
        id = 0,
    )
}
