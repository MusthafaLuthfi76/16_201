package com.example.final_pam.ui.viewmodel.kategori

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_pam.model.Kategori
import com.example.final_pam.repository.KategoriRepository
import com.example.final_pam.ui.view.kategori.DestinasiUpdateKategori
import kotlinx.coroutines.launch

class UpdateKategoriViewModel(
    savedStateHandle: SavedStateHandle,
    private val kategoriRepository: KategoriRepository
) : ViewModel() {

    var updateUiState by mutableStateOf(InsertKategoriUiState())
        private set

    private val _idKategori: String = checkNotNull(savedStateHandle[DestinasiUpdateKategori.ID_KATEGORI])

    init {
        viewModelScope.launch {
            updateUiState = kategoriRepository.getKategoriById(_idKategori).toUiStateKategori()
        }
    }

    fun updateInsertKategoriState(insertUiEvent: InsertKategoriUiEvent) {
        updateUiState = InsertKategoriUiState(insertUiEvent = insertUiEvent)
    }

    fun updateKategori() {
        viewModelScope.launch {
            try {
                kategoriRepository.updateKategori(_idKategori, updateUiState.insertUiEvent.toKategori())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}




