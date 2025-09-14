package com.example.dailypractice2.data.firebase

import com.example.dailypractice2.data.entity.JournalEntryEntity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Created by Clarence E Moore on 2025-09-13.
 *
 * Description:
 *
 *
 */
interface FirebaseJournalDataSource {

    suspend fun saveJournalEntry(entity: JournalEntryEntity): String

    suspend fun getAllJournalEntries(): List<JournalEntryEntity>

    suspend fun getJournalEntryById(id: String): JournalEntryEntity?

    suspend fun deleteJournalEntry(id: String)
}

class FirebaseJournalDataSourceImpl : FirebaseJournalDataSource {

    private val database: DatabaseReference = FirebaseDatabase.getInstance()
        .reference
        .child("journal_entries")

    override suspend fun saveJournalEntry(entity: JournalEntryEntity): String {
        return suspendCancellableCoroutine { continuation ->
            // Generate unique Firebase key
            val key = database.push().key
            if (key != null) {
                // create entity with Firebase-generated ID
                val entityWithKey = entity.copy(id = key.hashCode().toLong())
                database.child(key).setValue(entityWithKey)
                    .addOnSuccessListener {
                        continuation.resume(key)
                    }
                    .addOnFailureListener { exception ->
                        continuation.resumeWithException(exception)
                    }
            } else {
                continuation.resumeWithException(Exception("Failed to generate Firebase key"))
            }
        }
    }

    override suspend fun getAllJournalEntries(): List<JournalEntryEntity> {
        return suspendCancellableCoroutine { continuation ->
            database.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val entities = mutableListOf<JournalEntryEntity>()
                    // Iterate through firebase children and convert to entities
                    for (child in snapshot.children) {
                        child.getValue(JournalEntryEntity::class.java)?.let {
                            entities.add(it)
                        }
                    }
                    continuation.resume(entities)
                }

                override fun onCancelled(error: DatabaseError) {
                    continuation.resumeWithException(error.toException())
                }
            })
        }
    }

    override suspend fun getJournalEntryById(id: String): JournalEntryEntity? {
        return suspendCancellableCoroutine { continuation ->
            database.child(id).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val entity = snapshot.getValue(JournalEntryEntity::class.java)
                    continuation.resume(entity)
                }

                override fun onCancelled(error: DatabaseError) {
                    continuation.resumeWithException(error.toException())
                }
            })
        }
    }

    override suspend fun deleteJournalEntry(id: String) {
        suspendCancellableCoroutine<Unit> { continuation ->
            database.child(id).removeValue()
                .addOnSuccessListener {
                    continuation.resume(Unit)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
    }
}
