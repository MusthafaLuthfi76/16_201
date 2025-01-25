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

    fun insertPengeluaran() {
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
    val total: Int = 0,
    val catatan: String = ""
)

fun InsertPengeluaranUiEvent.toPengeluaran(): Pengeluaran = Pengeluaran(
    Id_Pengeluaran = idPengeluaran,
    Id_Aset = idAset,
    Id_Kategori = idKategori,
    tglTransaksi = tglTransaksi,
    total = total,
    catatan = catatan
)

fun Pengeluaran.toUiStatePengeluaran(): InsertPengeluaranUiState = InsertPengeluaranUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Pengeluaran.toInsertUiEvent(): InsertPengeluaranUiEvent = InsertPengeluaranUiEvent(
    idPengeluaran = Id_Pengeluaran,
    idAset = Id_Aset,
    idKategori = Id_Kategori,
    tglTransaksi = tglTransaksi,
    total = total,
    catatan = catatan
)

