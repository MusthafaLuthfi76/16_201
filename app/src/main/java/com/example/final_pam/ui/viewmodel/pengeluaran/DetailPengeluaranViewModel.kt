package com.example.final_pam.ui.viewmodel.pengeluaran

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_pam.model.Pengeluaran
import com.example.final_pam.repository.PengeluaranRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class PengeluaranDetailUiState {
    data class Success(val pengeluaran: Pengeluaran) : PengeluaranDetailUiState()
    object Error : PengeluaranDetailUiState()
    object Loading : PengeluaranDetailUiState()
}

class PengeluaranDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val pengeluaranRepository: PengeluaranRepository
) : ViewModel() {

    var pengeluaranDetailState: PengeluaranDetailUiState by mutableStateOf(PengeluaranDetailUiState.Loading)
        private set

    private val _idPengeluaran: String = checkNotNull(savedStateHandle["id_pengeluaran"])

    init {
        getPengeluaranById()
    }

    fun getPengeluaranById() {
        viewModelScope.launch {
            pengeluaranDetailState = PengeluaranDetailUiState.Loading
            pengeluaranDetailState = try {
                val pengeluaran = pengeluaranRepository.getPengeluaranById(_idPengeluaran)
                PengeluaranDetailUiState.Success(pengeluaran)
            } catch (e: IOException) {
                PengeluaranDetailUiState.Error
            } catch (e: HttpException) {
                PengeluaranDetailUiState.Error
            }
        }
    }

    fun deletePengeluaran(idPengeluaran: String) {
        viewModelScope.launch {
            try {
                pengeluaranRepository.deletePengeluaran(idPengeluaran)
            } catch (e: IOException) {
                pengeluaranDetailState = PengeluaranDetailUiState.Error
            } catch (e: HttpException) {
                pengeluaranDetailState = PengeluaranDetailUiState.Error
            }
        }
    }
}

