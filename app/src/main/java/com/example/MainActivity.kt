package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.navigation.TNTBusNavGraph
import com.example.ui.theme.TNTBusTheme
import com.example.ui.theme.TNTDarkBackground
import com.example.viewmodel.TNTBusViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: TNTBusViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TNTBusTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = TNTDarkBackground
                ) {
                    val navController = rememberNavController()
                    TNTBusNavGraph(
                        navController = navController,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}
