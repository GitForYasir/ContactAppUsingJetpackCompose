package com.example.contactappselfmade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.contactappselfmade.db.ContactDatabase
import com.example.contactappselfmade.repository.ContactRepoImpl
import com.example.contactappselfmade.screens.HomeScreen
import com.example.contactappselfmade.screens.HomeViewModel
import com.example.contactappselfmade.screens.HomeViewModelFactory
import com.example.contactappselfmade.ui.theme.ContactAppSelfMadeTheme

class MainActivity : ComponentActivity() {
    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(ContactRepoImpl(ContactDatabase.getDatabase(this)))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactAppSelfMadeTheme {
                HomeScreen(viewModel = viewModel)
            }
        }
    }
}