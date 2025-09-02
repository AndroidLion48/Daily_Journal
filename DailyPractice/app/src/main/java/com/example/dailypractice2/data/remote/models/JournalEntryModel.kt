package com.example.dailypractice2.data.remote.models


/**
 * Created by Clarence E Moore on 2025-04-20.
 *
 * Description:
 *
 *
 */
data class JournalEntryModel(
    val id: Int,
    val title : String,
    val content: String,
    val date: String,
)

//fun JournalEntryEntity.toDomainModel(): JournalEntryModel {
//    return JournalEntryModel(
//        title = title,
//        content = entry,
//        date = entryDate,
//        id = 0,
//    )
//}