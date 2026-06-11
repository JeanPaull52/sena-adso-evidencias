package com.example.noteplusadmin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.noteplusadmin.navigation.NotePlusNavGraph
import com.example.noteplusadmin.ui.theme.NotePlusAdminTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotePlusAdminTheme {
                val navController = rememberNavController()
                NotePlusNavGraph(navController = navController)
            }
        }
    }
}
