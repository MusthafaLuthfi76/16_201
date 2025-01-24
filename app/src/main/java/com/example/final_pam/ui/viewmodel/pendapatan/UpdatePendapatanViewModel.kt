package com.example.final_pam.ui.viewmodel.pendapatan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_pam.model.Pendapatan
import com.example.final_pam.repository.PendapatanRepository
import com.example.final_pam.ui.view.DestinasiUpdate
import kotlinx.coroutines.launch

class UpdatePendapatanViewModel(
    savedStateHandle: SavedStateHandle,
    private val pendapatanRepository: PendapatanRepository
) : ViewModel() {

    var updateUiState by mutableStateOf(UpdatePendapatanUiState())
        private set

    private val _idPendapatan: String = checkNotNull(savedStateHandle[DestinasiUpdate.ID_PENDAPATAN])

    init {
        viewModelScope.launch {
            updateUiState = pendapatanRepository.getPendapatanById(_idPendapatan)
                .toUiStatePendapatan()
        }
    }

    fun updatePendapatanState(updatePendapatanEvent: UpdatePendapatanUiEvent) {
        updateUiState = UpdatePendapatanUiState(updatePendapatanEvent = updatePendapatanEvent)
    }

    suspend fun updatePendapatan() {
        viewModelScope.launch {
            try {
                pendapatanRepository.updatePendapatan(_idPendapatan, updateUiState.updatePendapatanEvent.toPendapatan())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class UpdatePendapatanUiState(
    val updatePendapatanEvent: UpdatePendapatanUiEvent = UpdatePendapatanUiEvent()
)

data class UpdatePendapatanUiEvent(
    val idPendapatan: String = "",
    val idAset: String = "",
    val idKategori: String = "",
    val tglTransaksi: String = "",
    val total: String = "",
    val catatan: String = ""
)

fun UpdatePendapatanUiEvent.toPendapatan(): Pendapatan = Pendapatan(
    idPendapatan = idPendapatan,
    idAset = idAset,
    idKategori = idKategori,
    tglTransaksi = tglTransaksi,
    total = total,
    catatan = catatan
)

fun Pendapatan.toUiStatePendapatan(): UpdatePendapatanUiState = UpdatePendapatanUiState(
    updatePendapatanEvent = toUpdatePendapatanUiEvent()
)

fun Pendapatan.toUpdatePendapatanUiEvent(): UpdatePendapatanUiEvent = UpdatePendapatanUiEvent(
    idPendapatan = idPendapatan,
    idAset = idAset,
    idKategori = idKategori,
    tglTransaksi = tglTransaksi,
    total = total,
    catatan = catatan
)
