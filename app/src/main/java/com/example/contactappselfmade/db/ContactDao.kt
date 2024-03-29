package com.example.contactappselfmade.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contact)

    @Update()
    suspend fun updateContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Query("SELECT * FROM contact")
    fun getContact(): Flow<List<Contact>>

    @Query("SELECT * FROM contact WHERE firstName LIKE :query || '%' OR lastName LIKE :query || '%' OR phoneNumber LIKE :query || '%'")
    fun searchContacts(query: String): List<Contact>

//    @Query("SELECT * FROM contact WHERE id = :id")
//    fun getContactById(id: Long): Flow<Contact?>
}