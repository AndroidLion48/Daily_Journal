package com.example.dailypractice2.models

import com.example.dailypractice2.data.entity.JournalEntryEntity

/**
 * Data class representing a journal entry in the domain layer.
 *
 * @property title The title of the journal entry.
 * @property content The content of the journal entry.
 * @property date The date of the journal entry.
 * @property id The unique identifier of the journal entry.
 */
data class JournalEntryModel(
    val title : String,
    val content: String,
    val date: String,
    val id: Int
)

/**
 * Extension function to convert a [JournalEntryEntity] to a [JournalEntryModel].
 *
 * @return The corresponding [JournalEntryModel].
 */
fun JournalEntryEntity.toDomainModel(): JournalEntryModel {
    // Note: The 'id' field is currently hardcoded to 0.
    // This might need to be adjusted based on how IDs are handled in application.
    return JournalEntryModel(
        title = title,
        content = entry,
        date = entryDate,
        id = 0,
    )
}
