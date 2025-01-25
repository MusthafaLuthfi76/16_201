package com.example.final_pam.ui.viewmodel.pengeluaran

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_pam.model.Pengeluaran
import com.example.final_pam.repository.PengeluaranRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class PengeluaranHomeUiState {
    data class Success(val pengeluaran: List<Pengeluaran>) : PengeluaranHomeUiState()
    object Error : PengeluaranHomeUiState()
    object Loading : PengeluaranHomeUiState()
}

class PengeluaranHomeViewModel(private val pengeluaranRepository: PengeluaranRepository) : ViewModel() {
    var pengeluaranUIState: PengeluaranHomeUiState by mutableStateOf(PengeluaranHomeUiState.Loading)
        private set

    init {
        getPengeluaran()
    }

    fun getPengeluaran() {
        viewModelScope.launch {
            pengeluaranUIState = PengeluaranHomeUiState.Loading
            pengeluaranUIState = try {
                PengeluaranHomeUiState.Success(pengeluaranRepository.getPengeluaran().data)
            } catch (e: IOException) {
                PengeluaranHomeUiState.Error
            } catch (e: HttpException) {
                PengeluaranHomeUiState.Error
            }
        }
    }

    fun deletePengeluaran(idPengeluaran: String) {
        viewModelScope.launch {
            try {
                pengeluaranRepository.deletePengeluaran(idPengeluaran)
                getPengeluaran() // Refresh data setelah penghapusan
            } catch (e: IOException) {
                pengeluaranUIState = PengeluaranHomeUiState.Error
            } catch (e: HttpException) {
                pengeluaranUIState = PengeluaranHomeUiState.Error
            }
        }
    }
}

