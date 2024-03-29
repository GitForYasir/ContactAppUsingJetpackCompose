package com.example.contactappselfmade.repository

import com.example.contactappselfmade.db.Contact
import kotlinx.coroutines.flow.Flow

interface ContactRepo {
    suspend fun getContact(): Flow<List<Contact>>
    suspend fun insertContact(contact: Contact)
    suspend fun updateContact(contact: Contact)
    suspend fun deleteContact(contact: Contact)
    suspend fun searchContacts(query: String): List<Contact>
}