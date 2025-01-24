package com.example.final_pam.ui.viewmodel.pengeluaran

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_pam.repository.PengeluaranRepository
import kotlinx.coroutines.launch

class UpdatePengeluaranViewModel(
    savedStateHandle: SavedStateHandle,
    private val pengeluaranRepository: PengeluaranRepository
): ViewModel() {
    var updateUiState by mutableStateOf(InsertPengeluaranUiState())
        private set

    // Ambil idPengeluaran dari SavedStateHandle
    private val _idPengeluaran: Int = checkNotNull(savedStateHandle[DestinasiUpdate.ID_PENGELUARAN])

    init {
        viewModelScope.launch {
            // Ambil data pengeluaran berdasarkan idPengeluaran
            updateUiState = pengeluaranRepository.getPengeluaranById(_idPengeluaran)
                .toUiStatePengeluaran()
        }
    }

    fun updateInsertPengeluaranState(insertUiEvent: InsertPengeluaranUiEvent) {
        updateUiState = InsertPengeluaranUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun updatePengeluaran() {
        viewModelScope.launch {
            try {
                // Perbarui data pengeluaran berdasarkan idPengeluaran
                pengeluaranRepository.updatePengeluaran(_idPengeluaran, updateUiState.insertUiEvent.toPengeluaran())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

