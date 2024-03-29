package com.example.contactappselfmade.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.contactappselfmade.db.Contact
import com.example.contactappselfmade.repository.ContactRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val repo: ContactRepo): ViewModel() {
    private val _contacts: MutableStateFlow<List<Contact>> = MutableStateFlow(emptyList())
    val contacts = _contacts.asStateFlow()

    fun searchContacts(query: String){
        viewModelScope.launch(Dispatchers.IO) {
            val searchResult = repo.searchContacts(query)
            _contacts.emit(searchResult)
        }
    }

    init {
        getContacts()
    }
    private fun getContacts(){
        viewModelScope.launch(Dispatchers.IO){
            repo.getContact().collect{data->
                _contacts.update { data }
            }
        }
    }

    fun updateContact(contact: Contact){
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateContact(contact)
        }
    }

    fun deleteContact(contact: Contact){
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteContact(contact)
        }
    }

    fun insertContact(contact: Contact){
        viewModelScope.launch(Dispatchers.IO){
            repo.insertContact(contact)
        }
    }
}

class HomeViewModelFactory(private val repo: ContactRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}