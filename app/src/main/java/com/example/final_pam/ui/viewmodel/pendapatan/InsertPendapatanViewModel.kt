package com.example.final_pam.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_pam.model.Pendapatan
import com.example.final_pam.repository.PendapatanRepository
import kotlinx.coroutines.launch

class InsertPendapatanViewModel(private val pendapatanRepository: PendapatanRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertPendapatanUiState())
        private set

    fun updateInsertPendapatanState(insertUiEvent: InsertPendapatanUiEvent) {
        uiState = InsertPendapatanUiState(insertUiEvent = insertUiEvent)
    }

    fun insertPendapatan() {
        viewModelScope.launch {
            try {
                pendapatanRepository.insertPendapatan(uiState.insertUiEvent.toPendapatan())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertPendapatanUiState(
    val insertUiEvent: InsertPendapatanUiEvent = InsertPendapatanUiEvent()
)

data class InsertPendapatanUiEvent(
    val idPendapatan: String = "",
    val idAset: String = "",
    val idKategori: String = "",
    val tglTransaksi: String = "",
    val total: Int = 0,
    val catatan: String = ""
)

fun InsertPendapatanUiEvent.toPendapatan(): Pendapatan = Pendapatan(
    Id_Pendapatan = idPendapatan,
    Id_Aset = idAset,
    Id_Kategori = idKategori,
    tglTransaksi = tglTransaksi,
    total = total,
    catatan = catatan
)

fun Pendapatan.toUiStatePendapatan(): InsertPendapatanUiState = InsertPendapatanUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Pendapatan.toInsertUiEvent(): InsertPendapatanUiEvent = InsertPendapatanUiEvent(
    idPendapatan = Id_Pendapatan,
    idAset = Id_Aset,
    idKategori = Id_Kategori,
    tglTransaksi = tglTransaksi,
    total = total,
    catatan = catatan
)

