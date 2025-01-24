package com.example.final_pam.ui.viewmodel.kategori

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_pam.repository.KategoriRepository
import com.example.final_pam.ui.view.kategori.DestinasiUpdateKategori
import kotlinx.coroutines.launch

class UpdateKategoriViewModel(
    savedStateHandle: SavedStateHandle,
    private val kategoriRepository: KategoriRepository
): ViewModel() {
    var updateKategoriUiState by mutableStateOf(InsertKategoriUiState())
        private set

    // Ambil idKategori dari SavedStateHandle
    private val _idKategori: Int = checkNotNull(savedStateHandle[DestinasiUpdateKategori.ID_KATEGORI])

    init {
        viewModelScope.launch {
            // Ambil data kategori berdasarkan idKategori
            updateKategoriUiState = kategoriRepository.getKategoriById(_idKategori)
                .toUiStateKategori()
        }
    }

    fun updateInsertKategoriState(insertUiEvent: InsertKategoriUiEvent) {
        updateKategoriUiState = InsertKategoriUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun updateKategori() {
        viewModelScope.launch {
            try {
                // Perbarui data kategori berdasarkan idKategori
                kategoriRepository.updateKategori(_idKategori, updateKategoriUiState.insertUiEvent.toKategori())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

