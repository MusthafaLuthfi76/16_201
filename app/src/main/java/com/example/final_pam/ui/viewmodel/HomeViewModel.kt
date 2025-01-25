package com.example.final_pam.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_pam.repository.PendapatanRepository
import com.example.final_pam.repository.PengeluaranRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val pendapatanRepository: PendapatanRepository,
    private val pengeluaranRepository: PengeluaranRepository
) : ViewModel() {
    val saldo = mutableStateOf(0)
    val totalPendapatan = mutableStateOf(0)
    val totalPengeluaran = mutableStateOf(0)

    init {
        calculateSaldo()
    }

    private fun calculateSaldo() {
        viewModelScope.launch {
            try {
                // Hitung total pendapatan
                val pendapatan = pendapatanRepository.getPendapatan().data.sumOf { it.total }
                totalPendapatan.value = pendapatan

                // Hitung total pengeluaran
                val pengeluaran = pengeluaranRepository.getPengeluaran().data.sumOf { it.total }
                totalPengeluaran.value = pengeluaran

                // Hitung saldo
                saldo.value = pendapatan - pengeluaran
            } catch (e: Exception) {
                // Tangani error seperti menampilkan pesan ke UI
                saldo.value = 0
                totalPendapatan.value = 0
                totalPengeluaran.value = 0
            }
        }
    }
}

