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

sealed class HomePendapatanUiState {
    data class Success(val pendapatan: List<Pendapatan>) : HomePendapatanUiState()
    object Error : HomePendapatanUiState()
    object Loading : HomePendapatanUiState()
}

class HomePendapatanViewModel(private val pendapatanRepository: PendapatanRepository) : ViewModel() {
    var pendapatanUIState: HomePendapatanUiState by mutableStateOf(HomePendapatanUiState.Loading)
        private set

    init {
        getPendapatan()
    }

    fun getPendapatan() {
        viewModelScope.launch {
            pendapatanUIState = HomePendapatanUiState.Loading
            pendapatanUIState = try {
                HomePendapatanUiState.Success(pendapatanRepository.getPendapatan().data)
            } catch (e: IOException) {
                HomePendapatanUiState.Error
            } catch (e: HttpException) {
                HomePendapatanUiState.Error
            }
        }
    }

    fun deletePendapatan(idPendapatan: String) {
        viewModelScope.launch {
            try {
                pendapatanRepository.deletePendapatan(idPendapatan)
            } catch (e: IOException) {
                HomePendapatanUiState.Error
            } catch (e: HttpException) {
                HomePendapatanUiState.Error
            }
        }
    }
}
