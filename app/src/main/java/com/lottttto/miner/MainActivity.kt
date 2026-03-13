package com.lottttto.miner

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.lottttto.miner.ui.theme.LotttttoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Добавляем логирование для отладки
        Log.d("MainActivity", "onCreate started")
        
        setContent {
            LotttttoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
        
        Log.d("MainActivity", "setContent completed")
    }
}

@Composable
fun Greeting() {
    Text(
        text = "Lottttto Miner",
        fontSize = 24.sp
    )
}
