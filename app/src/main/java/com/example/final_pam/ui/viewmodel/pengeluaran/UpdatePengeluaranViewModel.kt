package com.example.final_pam.ui.viewmodel.pengeluaran


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_pam.model.Pengeluaran
import com.example.final_pam.repository.PengeluaranRepository
import com.example.final_pam.ui.view.DestinasiUpdate
import kotlinx.coroutines.launch

class UpdatePengeluaranViewModel(
    savedStateHandle: SavedStateHandle,
    private val pengeluaranRepository: PengeluaranRepository
) : ViewModel() {

    var updateUiState by mutableStateOf(UpdatePengeluaranUiState())
        private set

    private val _idPengeluaran: String = checkNotNull(savedStateHandle[DestinasiUpdate.ID_PENGELUARAN])

    init {
        viewModelScope.launch {
            updateUiState = pengeluaranRepository.getPengeluaranById(_idPengeluaran)
                .toUiStatePengeluaran()
        }
    }

    fun updatePengeluaranState(updatePengeluaranEvent: UpdatePengeluaranUiEvent) {
        updateUiState = UpdatePengeluaranUiState(updatePengeluaranEvent = updatePengeluaranEvent)
    }

    suspend fun updatePengeluaran() {
        viewModelScope.launch {
            try {
                pengeluaranRepository.updatePengeluaran(_idPengeluaran, updateUiState.updatePengeluaranEvent.toPengeluaran())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class UpdatePengeluaranUiState(
    val updatePengeluaranEvent: UpdatePengeluaranUiEvent = UpdatePengeluaranUiEvent()
)

data class UpdatePengeluaranUiEvent(
    val idPengeluaran: String = "",
    val idAset: String = "",
    val idKategori: String = "",
    val tglTransaksi: String = "",
    val total: String = "",
    val catatan: String = ""
)

fun UpdatePengeluaranUiEvent.toPengeluaran(): Pengeluaran = Pengeluaran(
    idPengeluaran = idPengeluaran,
    idAset = idAset,
    idKategori = idKategori,
    tglTransaksi = tglTransaksi,
    total = total,
    catatan = catatan
)

fun Pengeluaran.toUiStatePengeluaran(): UpdatePengeluaranUiState = UpdatePengeluaranUiState(
    updatePengeluaranEvent = toUpdatePengeluaranUiEvent()
)

fun Pengeluaran.toUpdatePengeluaranUiEvent(): UpdatePengeluaranUiEvent = UpdatePengeluaranUiEvent(
    idPengeluaran = idPengeluaran,
    idAset = idAset,
    idKategori = idKategori,
    tglTransaksi = tglTransaksi,
    total = total,
    catatan = catatan
)
