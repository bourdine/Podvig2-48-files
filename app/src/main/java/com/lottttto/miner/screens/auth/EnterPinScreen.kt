package com.lottttto.miner.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lottttto.miner.viewmodels.PinViewModel

@Composable
fun EnterPinScreen(
    onPinSuccess: () -> Unit,
    onForgotPin: () -> Unit,
    viewModel: PinViewModel = hiltViewModel()
) {
    var pin by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Lottttto",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        Text(
            text = "Welcome back!",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Enter your 4-digit PIN",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        OutlinedTextField(
            value = pin,
            onValueChange = { if (it.length <= 4) pin = it.filter { c -> c.isDigit() } },
            label = { Text("PIN") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.NumberPassword)
        )
        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                if (pin.length != 4) {
                    errorMessage = "PIN must be 4 digits"
                } else {
                    viewModel.checkPin(pin) { isValid ->
                        if (isValid) onPinSuccess()
                        else errorMessage = "Incorrect PIN"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Unlock")
        }
        TextButton(onClick = onForgotPin) {
            Text("Forgot PIN? Reset account")
        }
    }
}
