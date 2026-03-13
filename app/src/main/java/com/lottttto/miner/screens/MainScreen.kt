package com.lottttto.miner.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.lottttto.miner.models.MiningMode
import com.lottttto.miner.viewmodels.MainViewModel

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel()
) {
    val computingUsage by viewModel.computingUsage.collectAsState()
    val tasks by viewModel.tasks.collectAsState()
    var menuExpanded by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Верхняя панель
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Lottttto", style = MaterialTheme.typography.headlineLarge)
            
            Box {
                IconButton(onClick = { menuExpanded = true }) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings")
                }
                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Wallet") },
                        onClick = {
                            menuExpanded = false
                            navController.navigate("wallet")
                        }
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Информация о кошельке
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Monero Wallet", style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "No wallet added yet",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Computing usage
        Text("Computing Usage: $computingUsage%", style = MaterialTheme.typography.titleMedium)
        Slider(
            value = computingUsage.toFloat(),
            onValueChange = { viewModel.setComputingUsage(it.toInt()) },
            valueRange = 0f..100f,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Mining tasks
        Text("Mining Tasks", style = MaterialTheme.typography.titleLarge)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(tasks.size) { index ->
                val task = tasks[index]
                val label = if (task.mode == MiningMode.POOL) "Pool" else "Solo"
                
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(label, style = MaterialTheme.typography.labelLarge)
                    Surface(
                        modifier = Modifier
                            .width(40.dp)
                            .height(150.dp),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {}
                    Text("${task.weight}%")
                }
            }
        }
    }
}
