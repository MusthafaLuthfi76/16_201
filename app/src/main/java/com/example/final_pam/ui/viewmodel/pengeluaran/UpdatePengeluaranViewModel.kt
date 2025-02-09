package com.example.final_pam.ui.viewmodel.pengeluaran

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_pam.model.Pengeluaran
import com.example.final_pam.repository.PengeluaranRepository
import com.example.final_pam.ui.view.pengeluaran.DestinasiUpdatePengeluaran
import com.example.final_pam.ui.viewmodel.pengeluaran.InsertPengeluaranUiEvent
import com.example.final_pam.ui.viewmodel.pengeluaran.InsertPengeluaranUiState
import com.example.final_pam.ui.viewmodel.pengeluaran.toPengeluaran
import com.example.final_pam.ui.viewmodel.pengeluaran.toUiStatePengeluaran
import kotlinx.coroutines.launch

class UpdatePengeluaranViewModel(
    savedStateHandle: SavedStateHandle,
    private val pengeluaranRepository: PengeluaranRepository
) : ViewModel() {

    var updateUiState by mutableStateOf(InsertPengeluaranUiState())
        private set

    private val _idPengeluaran: String = checkNotNull(savedStateHandle[DestinasiUpdatePengeluaran.ID_PENGELUARAN])

    init {
        viewModelScope.launch {
            updateUiState = pengeluaranRepository.getPengeluaranById(_idPengeluaran).toUiStatePengeluaran()
        }
    }

    fun updateInsertPengeluaranState(insertUiEvent: InsertPengeluaranUiEvent) {
        updateUiState = InsertPengeluaranUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun updatePengeluaran() {
        viewModelScope.launch {
            try {
                pengeluaranRepository.updatePengeluaran(_idPengeluaran, updateUiState.insertUiEvent.toPengeluaran())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
