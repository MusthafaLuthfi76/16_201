package com.example.final_pam.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_pam.model.Aset
import com.example.final_pam.repository.AsetRepository
import com.example.final_pam.ui.view.aset.DestinasiUpdateAset
import com.example.final_pam.ui.viewmodel.aset.InsertAsetUiEvent
import com.example.final_pam.ui.viewmodel.aset.InsertAsetUiState
import com.example.final_pam.ui.viewmodel.aset.toAset
import com.example.final_pam.ui.viewmodel.aset.toUiStateAset
import kotlinx.coroutines.launch

class UpdateAsetViewModel(
    savedStateHandle: SavedStateHandle,
    private val asetRepository: AsetRepository
) : ViewModel() {

    var updateUiState by mutableStateOf(InsertAsetUiState())
        private set

    private val _idAset: String = checkNotNull(savedStateHandle[DestinasiUpdateAset.ID_ASET])

    init {
        viewModelScope.launch {
            updateUiState = asetRepository.getAsetById(_idAset).toUiStateAset()
        }
    }

    fun updateInsertAsetState(insertUiEvent: InsertAsetUiEvent) {
        updateUiState = InsertAsetUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun updateAset() {
        viewModelScope.launch {
            try {
                asetRepository.updateAset(_idAset, updateUiState.insertUiEvent.toAset())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

