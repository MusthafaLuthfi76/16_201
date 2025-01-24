package com.example.final_pam.ui.viewmodel.pengeluaran

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_pam.model.Pengeluaran
import com.example.final_pam.repository.PengeluaranRepository
import kotlinx.coroutines.launch

class InsertPengeluaranViewModel(private val pengeluaranRepository: PengeluaranRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertPengeluaranUiState())
        private set

    fun updateInsertPengeluaranState(insertUiEvent: InsertPengeluaranUiEvent) {
        uiState = InsertPengeluaranUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertPengeluaran() {
        viewModelScope.launch {
            try {
                pengeluaranRepository.insertPengeluaran(uiState.insertUiEvent.toPengeluaran())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertPengeluaranUiState(
    val insertUiEvent: InsertPengeluaranUiEvent = InsertPengeluaranUiEvent()
)

data class InsertPengeluaranUiEvent(
    val idPengeluaran: String = "",
    val idAset: String = "",
    val idKategori: String = "",
    val tglTransaksi: String = "",
    val total: String = "",
    val catatan: String = ""
)

fun InsertPengeluaranUiEvent.toPengeluaran(): Pengeluaran = Pengeluaran(
    idPengeluaran = idPengeluaran,
    idAset = idAset,
    idKategori = idKategori,
    tglTransaksi = tglTransaksi,
    total = total,
    catatan = catatan
)

fun Pengeluaran.toUiStatePengeluaran(): InsertPengeluaranUiState = InsertPengeluaranUiState(
    insertUiEvent = toInsertPengeluaranUiEvent()
)

fun Pengeluaran.toInsertPengeluaranUiEvent(): InsertPengeluaranUiEvent = InsertPengeluaranUiEvent(
    idPengeluaran = idPengeluaran,
    idAset = idAset,
    idKategori = idKategori,
    tglTransaksi = tglTransaksi,
    total = total,
    catatan = catatan
)
