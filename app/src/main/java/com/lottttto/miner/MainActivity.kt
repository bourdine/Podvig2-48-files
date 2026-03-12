package com.lottttto.miner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lottttto.miner.ui.theme.LotttttoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LotttttoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Lottttto Miner",
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        
                        Text(
                            text = "Monero Mining Application",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "Status: Ready",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = "Waiting for user interaction...",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                        
                        Button(
                            onClick = { /* TODO: Add mining start logic */ },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Start Mining")
                        }
                    }
                }
            }
        }
    }
}
