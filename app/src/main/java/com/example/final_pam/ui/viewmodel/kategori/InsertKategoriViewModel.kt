package com.example.final_pam.ui.viewmodel.kategori

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_pam.model.Kategori
import com.example.final_pam.repository.KategoriRepository
import kotlinx.coroutines.launch

class InsertKategoriViewModel(private val kategoriRepository: KategoriRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertKategoriUiState())
        private set

    fun updateInsertKategoriState(insertUiEvent: InsertKategoriUiEvent) {
        uiState = InsertKategoriUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertKategori() {
        viewModelScope.launch {
            try {
                kategoriRepository.insertKategori(uiState.insertUiEvent.toKategori())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertKategoriUiState(
    val insertUiEvent: InsertKategoriUiEvent = InsertKategoriUiEvent()
)

data class InsertKategoriUiEvent(
    val idKategori: String = "",
    val namaKategori: String = ""
)

fun InsertKategoriUiEvent.toKategori(): Kategori = Kategori(
    idKategori = idKategori,
    namaKategori = namaKategori
)

fun Kategori.toUiStateKategori(): InsertKategoriUiState = InsertKategoriUiState(
    insertUiEvent = toInsertKategoriUiEvent()
)

fun Kategori.toInsertKategoriUiEvent(): InsertKategoriUiEvent = InsertKategoriUiEvent(
    idKategori = idKategori,
    namaKategori = namaKategori
)
