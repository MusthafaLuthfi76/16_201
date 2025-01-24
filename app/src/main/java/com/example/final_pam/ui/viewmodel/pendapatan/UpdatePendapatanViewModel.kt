package com.example.final_pam.ui.viewmodel.pendapatan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_pam.repository.PendapatanRepository
import com.example.final_pam.ui.viewmodel.InsertPendapatanUiEvent
import com.example.final_pam.ui.viewmodel.InsertPendapatanUiState
import com.example.final_pam.ui.viewmodel.toPendapatan
import com.example.final_pam.ui.viewmodel.toUiStatePendapatan
import kotlinx.coroutines.launch

class UpdatePendapatanViewModel(
    savedStateHandle: SavedStateHandle,
    private val pendapatanRepository: PendapatanRepository
): ViewModel() {
    var updateUiState by mutableStateOf(InsertPendapatanUiState())
        private set

    // Ambil idPendapatan dari SavedStateHandle
    private val _idPendapatan: Int = checkNotNull(savedStateHandle[DestinasiUpdate.ID_PENDAPATAN])

    init {
        viewModelScope.launch {
            // Ambil data pendapatan berdasarkan idPendapatan
            updateUiState = pendapatanRepository.getPendapatanById(_idPendapatan)
                .toUiStatePendapatan()
        }
    }

    fun updateInsertPendapatanState(insertUiEvent: InsertPendapatanUiEvent) {
        updateUiState = InsertPendapatanUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun updatePendapatan() {
        viewModelScope.launch {
            try {
                // Perbarui data pendapatan berdasarkan idPendapatan
                pendapatanRepository.updatePendapatan(_idPendapatan, updateUiState.insertUiEvent.toPendapatan())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

