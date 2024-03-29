package com.example.contactappselfmade.repository

import com.example.contactappselfmade.db.Contact
import com.example.contactappselfmade.db.ContactDatabase
import kotlinx.coroutines.flow.Flow

class ContactRepoImpl(database: ContactDatabase): ContactRepo {
    private val dao = database.contactDao

    override suspend fun getContact(): Flow<List<Contact>> = dao.getContact()
    override suspend fun insertContact(contact: Contact) = dao.insertContact(contact)
    override suspend fun updateContact(contact: Contact) = dao.updateContact(contact)
    override suspend fun deleteContact(contact: Contact) = dao.deleteContact(contact)
    override suspend fun searchContacts(query: String): List<Contact> {
        // Implement search functionality using DAO's query method
        return dao.searchContacts(query)
    }
}