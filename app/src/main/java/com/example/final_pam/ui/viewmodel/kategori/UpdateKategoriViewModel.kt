package com.example.final_pam.ui.viewmodel.kategori

package com.example.final_pam.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_pam.model.Kategori
import com.example.final_pam.repository.KategoriRepository
import com.example.final_pam.ui.view.DestinasiUpdate
import kotlinx.coroutines.launch

class UpdateKategoriViewModel(
    savedStateHandle: SavedStateHandle,
    private val kategoriRepository: KategoriRepository
) : ViewModel() {
    var updateUiState by mutableStateOf(UpdateKategoriUiState())
        private set

    private val _idKategori: String = checkNotNull(savedStateHandle[DestinasiUpdate.ID_KATEGORI])

    init {
        viewModelScope.launch {
            // Mengambil data Kategori berdasarkan ID
            updateUiState = kategoriRepository.getKategoriById(_idKategori)
                .toUiStateKategori()
        }
    }

    fun updateKategoriState(updateKategoriEvent: UpdateKategoriUiEvent) {
        updateUiState = UpdateKategoriUiState(updateKategoriEvent = updateKategoriEvent)
    }

    suspend fun updateKategori() {
        viewModelScope.launch {
            try {
                kategoriRepository.updateKategori(_idKategori, updateUiState.updateKategoriEvent.toKategori())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class UpdateKategoriUiState(
    val updateKategoriEvent: UpdateKategoriUiEvent = UpdateKategoriUiEvent()
)

data class UpdateKategoriUiEvent(
    val idKategori: String = "",
    val namaKategori: String = ""
)

fun UpdateKategoriUiEvent.toKategori(): Kategori = Kategori(
    idKategori = idKategori,
    namaKategori = namaKategori
)

fun Kategori.toUiStateKategori(): UpdateKategoriUiState = UpdateKategoriUiState(
    updateKategoriEvent = toUpdateKategoriUiEvent()
)

fun Kategori.toUpdateKategoriUiEvent(): UpdateKategoriUiEvent = UpdateKategoriUiEvent(
    idKategori = idKategori,
    namaKategori = namaKategori
)
