package com.example.caravanasfutebol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.caravanasfutebol.ui.screens.AppContent
import com.example.caravanasfutebol.ui.theme.CaravanasTheme
import com.example.caravanasfutebol.viewmodel.AuthViewModel
import com.example.caravanasfutebol.viewmodel.CaravanaViewModel

class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    private val caravanaViewModel: CaravanaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaravanasTheme {
                AppContent(
                    authViewModel = authViewModel,
                    caravanaViewModel = caravanaViewModel
                )
            }
        }
    }
}