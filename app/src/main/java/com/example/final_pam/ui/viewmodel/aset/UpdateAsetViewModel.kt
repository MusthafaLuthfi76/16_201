package com.example.final_pam.ui.viewmodel.aset

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_pam.model.Aset
import com.example.final_pam.repository.AsetRepository
import com.example.final_pam.ui.view.DestinasiUpdate
import kotlinx.coroutines.launch

class UpdateAsetViewModel(
    savedStateHandle: SavedStateHandle,
    private val asetRepository: AsetRepository
) : ViewModel() {
    var updateUiState by mutableStateOf(UpdateAsetUiState())
        private set

    private val _idAset: String = checkNotNull(savedStateHandle[DestinasiUpdate.ID_ASET])

    init {
        viewModelScope.launch {
            // Mengambil data Aset berdasarkan ID
            updateUiState = asetRepository.getAsetById(_idAset)
                .toUiStateAset()
        }
    }

    fun updateAsetState(updateAsetEvent: UpdateAsetUiEvent) {
        updateUiState = UpdateAsetUiState(updateAsetEvent = updateAsetEvent)
    }

    suspend fun updateAset() {
        viewModelScope.launch {
            try {
                asetRepository.updateAset(_idAset, updateUiState.updateAsetEvent.toAset())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class UpdateAsetUiState(
    val updateAsetEvent: UpdateAsetUiEvent = UpdateAsetUiEvent()
)

data class UpdateAsetUiEvent(
    val idAset: String = "",
    val namaAset: String = ""
)

fun UpdateAsetUiEvent.toAset(): Aset = Aset(
    idAset = idAset,
    namaAset = namaAset
)

fun Aset.toUiStateAset(): UpdateAsetUiState = UpdateAsetUiState(
    updateAsetEvent = toUpdateAsetUiEvent()
)

fun Aset.toUpdateAsetUiEvent(): UpdateAsetUiEvent = UpdateAsetUiEvent(
    idAset = idAset,
    namaAset = namaAset
)
