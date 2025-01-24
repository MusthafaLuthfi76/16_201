package com.example.final_pam.ui.viewmodel.pendapatan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_pam.model.Pendapatan
import com.example.final_pam.repository.PendapatanRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class DetailPendapatanUiState {
    data class Success(val pendapatan: Pendapatan) : DetailPendapatanUiState()
    object Error : DetailPendapatanUiState()
    object Loading : DetailPendapatanUiState()
}

class DetailPendapatanViewModel(
    savedStateHandle: SavedStateHandle,
    private val pendapatanRepository: PendapatanRepository
) : ViewModel() {

    var pendapatanDetailState: DetailPendapatanUiState by mutableStateOf(DetailPendapatanUiState.Loading)
        private set

    private val _idPendapatan: String = checkNotNull(savedStateHandle["id_pendapatan"])

    init {
        getPendapatanById()
    }

    fun getPendapatanById() {
        viewModelScope.launch {
            pendapatanDetailState = DetailPendapatanUiState.Loading
            pendapatanDetailState = try {
                val pendapatan = pendapatanRepository.getPendapatanById(_idPendapatan)
                DetailPendapatanUiState.Success(pendapatan)
            } catch (e: IOException) {
                DetailPendapatanUiState.Error
            } catch (e: HttpException) {
                DetailPendapatanUiState.Error
            }
        }
    }

    fun deletePendapatan(idPendapatan: String) {
        viewModelScope.launch {
            try {
                pendapatanRepository.deletePendapatan(idPendapatan)
            } catch (e: IOException) {
                DetailPendapatanUiState.Error
            } catch (e: HttpException) {
                DetailPendapatanUiState.Error
            }
        }
    }
}
