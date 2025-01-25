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

sealed class PendapatanDetailUiState {
    data class Success(val pendapatan: Pendapatan) : PendapatanDetailUiState()
    object Error : PendapatanDetailUiState()
    object Loading : PendapatanDetailUiState()
}

class PendapatanDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val pendapatanRepository: PendapatanRepository
) : ViewModel() {

    var pendapatanDetailState: PendapatanDetailUiState by mutableStateOf(PendapatanDetailUiState.Loading)
        private set

    private val _idPendapatan: String = checkNotNull(savedStateHandle["id_pendapatan"])

    init {
        getPendapatanById()
    }

    fun getPendapatanById() {
        viewModelScope.launch {
            pendapatanDetailState = PendapatanDetailUiState.Loading
            pendapatanDetailState = try {
                val pendapatan = pendapatanRepository.getPendapatanById(_idPendapatan)
                PendapatanDetailUiState.Success(pendapatan)
            } catch (e: IOException) {
                PendapatanDetailUiState.Error
            } catch (e: HttpException) {
                PendapatanDetailUiState.Error
            }
        }
    }

    fun deletePendapatan(idPendapatan: String) {
        viewModelScope.launch {
            try {
                pendapatanRepository.deletePendapatan(idPendapatan)
            } catch (e: IOException) {
                pendapatanDetailState = PendapatanDetailUiState.Error
            } catch (e: HttpException) {
                pendapatanDetailState = PendapatanDetailUiState.Error
            }
        }
    }
}

