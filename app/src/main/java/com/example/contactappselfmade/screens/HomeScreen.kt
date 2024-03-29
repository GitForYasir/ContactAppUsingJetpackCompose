package com.example.contactappselfmade.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.contactappselfmade.R
import com.example.contactappselfmade.db.Contact


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
//    viewModel: HomeViewModel
//    OR
    viewModel: HomeViewModel = viewModel()
) {
    val contacts by viewModel.contacts.collectAsState()

    val (dialogOpen, setDialogOpen) = remember {
        mutableStateOf(false)
    }
    val (firstName, setFirstName) = remember {
        mutableStateOf("")
    }
    val (lastName, setLastName) = remember {
        mutableStateOf("")
    }
    val (phoneNumber, setPhoneNumber) = remember {
        mutableStateOf("")
    }
    val (editContact, setEditContact) = remember {
        mutableStateOf<Contact?>(null)
    }
    val btnText = remember { mutableStateOf("Add Contact") }
    var searchQuery by remember { mutableStateOf("") }
    var active by remember {
        mutableStateOf(false)
    }

    if (dialogOpen) {
        Dialog(onDismissRequest = {
            setDialogOpen(false)
            setFirstName("")
            setLastName("")
            setPhoneNumber("")
        }) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xff81deea))
                    .padding(8.dp)
            ) {
                OutlinedTextField(
                    value = firstName,
                    onValueChange = { setFirstName(it) },
                    label = { Text(text = "first name") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        focusedBorderColor = Color.Black,
                        focusedLabelColor = Color.Black,
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = lastName,
                    onValueChange = { setLastName(it) },
                    label = { Text(text = "last name") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        focusedBorderColor = Color.Black,
                        focusedLabelColor = Color.Black,
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { setPhoneNumber(it) },
                    label = { Text(text = "phone number") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        focusedBorderColor = Color.Black,
                        focusedLabelColor = Color.Black,
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (firstName.isNotEmpty() && lastName.isNotEmpty() && phoneNumber.isNotEmpty()) {
                            if (editContact != null) {
                                viewModel.updateContact(
                                    editContact.copy(
                                        firstName = firstName,
                                        lastName = lastName,
                                        phoneNumber = phoneNumber
                                    )
                                )
                                setFirstName("")
                                setLastName("")
                                setPhoneNumber("")
                            } else {
                                viewModel.insertContact(
                                    Contact(
                                        firstName = firstName,
                                        lastName = lastName,
                                        phoneNumber = phoneNumber
                                    )
                                )
                                setFirstName("")
                                setLastName("")
                                setPhoneNumber("")
                            }
                            setDialogOpen(false)
                        }
                    },
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Text(text = btnText.value, color = Color.Black)
                }
            }
        }
    }
    // Function to filter contacts based on search query
    fun filterContacts(contacts: List<Contact>, query: String): List<Contact> {
        return if (query.isEmpty()) {
            contacts
        } else {
            contacts.filter { contact ->
                contact.firstName.contains(query, ignoreCase = true) ||
                        contact.lastName.contains(query, ignoreCase = true) ||
                        contact.phoneNumber.contains(query, ignoreCase = true)
            }
        }
    }

    Scaffold(containerColor = Color.White,
        modifier = Modifier.padding(6.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    btnText.value = "Add Contact"
                    setDialogOpen(true)
                },
                contentColor = Color.Black,
                containerColor = Color(0xff81deea)
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }) { paddings ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddings)
        ) {
            Column {
                Text(
                    text = "Contact App",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 16.dp)
                )
                DockedSearchBar(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    onSearch = { viewModel.searchContacts(it) },
                    active = active,
                    onActiveChange = {
                        active = it
                    },
                    placeholder = {
                        Text(text = "Search Contact")
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "search")
                    },
                    trailingIcon = {
                        Row {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    painter = painterResource(R.drawable.mic),
                                    contentDescription = "mic"
                                )
                            }
                            if (active) {
                                IconButton(
                                    onClick = {
                                        if (searchQuery.isNotEmpty()) searchQuery = "" else active =
                                            false
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Close,
                                        contentDescription = "Close"
                                    )
                                }

                            }
                        }
                    },
                    modifier = Modifier.padding(bottom = 5.dp),
                ) {
                    ContactListLazyColumn(
                        contacts = filterContacts(contacts, searchQuery),
                        onContactClick = { contact ->
                            // Handle contact click action
                            setFirstName(contact.firstName)
                            setLastName(contact.lastName)
                            setPhoneNumber(contact.phoneNumber)
                            setEditContact(contact)
                            btnText.value = "Update Contact"
                            setDialogOpen(true)
                        },
                        onDeleteClick = {contact ->
                            viewModel.deleteContact(contact)
                        }
                    )
                }
                if (!active) {
                    ContactListLazyColumn(
                        contacts = contacts,
                        onContactClick = { contact ->
                            // Handle contact click action
                            setFirstName(contact.firstName)
                            setLastName(contact.lastName)
                            setPhoneNumber(contact.phoneNumber)
                            setEditContact(contact)
                            btnText.value = "Update Contact"
                            setDialogOpen(true)
                        },
                        onDeleteClick = {contact ->
                            viewModel.deleteContact(contact)
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun ContactListLazyColumn(
    contacts: List<Contact>,
    onContactClick: (Contact ) -> Unit,
    onDeleteClick: (Contact) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(contacts) { contact ->
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .background(
                        Color(0xff81deea),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clickable {
                        onContactClick(contact)
                    }
            ) {
                Column(Modifier.padding(8.dp)) {
                    Row {
                        Icon(
                            Icons.Default.AccountCircle,
                            contentDescription = null,
                            Modifier
                                .padding(end = 10.dp)
                                .size(22.dp),
                            tint = Color.DarkGray,
                        )
                        Text(text = contact.firstName, color = Color.Black)
                        Spacer(modifier = Modifier.padding(3.dp))
                        Text(text = contact.lastName, color = Color.Black)
                    }
                    Spacer(modifier = Modifier.padding(3.dp))
                    Row {
                        Icon(
                            Icons.Default.Call,
                            contentDescription = null,
                            Modifier
                                .padding(end = 10.dp)
                                .size(22.dp),
                            tint = Color.DarkGray

                        )
                        Text(
                            text = contact.phoneNumber,
                            color = Color.Black,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(6.dp)
                                .clickable {
                                    onDeleteClick(contact)
                                }
                        )
                    }
                }
            }
        }
    }
}
