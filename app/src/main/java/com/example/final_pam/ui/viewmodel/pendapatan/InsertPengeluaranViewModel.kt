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

    suspend fun insertPendapatan() {
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
    val idPendapatan: Int = 0,
    val idAset: Int = 0,
    val idKategori: Int = 0,
    val tglTransaksi: String = "",
    val total: Int = 0,
    val catatan: String = ""
)

fun InsertPendapatanUiEvent.toPendapatan(): Pendapatan = Pendapatan(
    idPendapatan = idPendapatan,
    idAset = idAset,
    idKategori = idKategori,
    tglTransaksi = tglTransaksi,
    total = total,
    catatan = catatan
)

fun Pendapatan.toUiStatePendapatan(): InsertPendapatanUiState = InsertPendapatanUiState(
    insertUiEvent = toInsertPendapatanUiEvent()
)

fun Pendapatan.toInsertPendapatanUiEvent(): InsertPendapatanUiEvent = InsertPendapatanUiEvent(
    idPendapatan = idPendapatan,
    idAset = idAset,
    idKategori = idKategori,
    tglTransaksi = tglTransaksi,
    total = total,
    catatan = catatan
)
