package com.example.final_pam.ui.viewmodel.aset

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_pam.model.Aset
import com.example.final_pam.repository.AsetRepository
import kotlinx.coroutines.launch

class InsertAsetViewModel(private val asetRepository: AsetRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertAsetUiState())
        private set

    fun updateInsertAsetState(insertUiEvent: InsertAsetUiEvent) {
        uiState = InsertAsetUiState(insertUiEvent = insertUiEvent)
    }

    fun insertAset() {
        viewModelScope.launch {
            try {
                asetRepository.insertAset(uiState.insertUiEvent.toAset())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertAsetUiState(
    val insertUiEvent: InsertAsetUiEvent = InsertAsetUiEvent()
)

data class InsertAsetUiEvent(
    val idAset: String = "",
    val namaAset: String = ""
)

fun InsertAsetUiEvent.toAset(): Aset = Aset(
    Id_Aset = idAset,
    Nama_aset = namaAset
)

fun Aset.toUiStateAset(): InsertAsetUiState = InsertAsetUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Aset.toInsertUiEvent(): InsertAsetUiEvent = InsertAsetUiEvent(
    idAset = Id_Aset,
    namaAset = Nama_aset
)
