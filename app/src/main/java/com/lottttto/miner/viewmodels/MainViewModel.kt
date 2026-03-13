package com.lottttto.miner.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lottttto.miner.models.CoinType
import com.lottttto.miner.models.MiningMode
import com.lottttto.miner.models.MiningTask
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    
    private val _tasks = MutableStateFlow(
        listOf(
            MiningTask(CoinType.MONERO, MiningMode.POOL),
            MiningTask(CoinType.MONERO, MiningMode.SOLO)
        ).toMutableList()
    )
    val tasks: StateFlow<List<MiningTask>> = _tasks.asStateFlow()
    
    private val _computingUsage = MutableStateFlow(15)
    val computingUsage: StateFlow<Int> = _computingUsage.asStateFlow()
    
    fun setComputingUsage(percent: Int) {
        _computingUsage.value = percent.coerceIn(0, 100)
    }
    
    fun updateTaskWeight(index: Int, newWeight: Int) {
        if (index !in _tasks.value.indices) return
        _tasks.value = _tasks.value.toMutableList().apply {
            this[index] = this[index].copy(weight = newWeight.coerceIn(0, 100))
        }
    }
}
