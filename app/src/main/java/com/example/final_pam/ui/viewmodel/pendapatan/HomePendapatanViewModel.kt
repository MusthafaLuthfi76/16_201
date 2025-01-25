package com.example.final_pam.ui.viewmodel.pendapatan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_pam.model.Pendapatan
import com.example.final_pam.repository.PendapatanRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class PendapatanHomeUiState {
    data class Success(val pendapatan: List<Pendapatan>) : PendapatanHomeUiState()
    object Error : PendapatanHomeUiState()
    object Loading : PendapatanHomeUiState()
}

class PendapatanHomeViewModel(private val pendapatanRepository: PendapatanRepository) : ViewModel() {
    var pendapatanUIState: PendapatanHomeUiState by mutableStateOf(PendapatanHomeUiState.Loading)
        private set

    init {
        getPendapatan()
    }

    fun getPendapatan() {
        viewModelScope.launch {
            pendapatanUIState = PendapatanHomeUiState.Loading
            pendapatanUIState = try {
                PendapatanHomeUiState.Success(pendapatanRepository.getPendapatan().data)
            } catch (e: IOException) {
                PendapatanHomeUiState.Error
            } catch (e: HttpException) {
                PendapatanHomeUiState.Error
            }
        }
    }

    fun deletePendapatan(idPendapatan: String) {
        viewModelScope.launch {
            try {
                pendapatanRepository.deletePendapatan(idPendapatan)
                getPendapatan() // Refresh data setelah penghapusan
            } catch (e: IOException) {
                pendapatanUIState = PendapatanHomeUiState.Error
            } catch (e: HttpException) {
                pendapatanUIState = PendapatanHomeUiState.Error
            }
        }
    }
}

