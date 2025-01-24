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

sealed class HomePengeluaranUiState {
    data class Success(val pengeluaran: List<Pengeluaran>) : HomePengeluaranUiState()
    object Error : HomePengeluaranUiState()
    object Loading : HomePengeluaranUiState()
}

class HomePengeluaranViewModel(private val pengeluaranRepository: PengeluaranRepository) : ViewModel() {
    var pengeluaranUIState: HomePengeluaranUiState by mutableStateOf(HomePengeluaranUiState.Loading)
        private set

    init {
        getPengeluaran()
    }

    fun getPengeluaran() {
        viewModelScope.launch {
            pengeluaranUIState = HomePengeluaranUiState.Loading
            pengeluaranUIState = try {
                HomePengeluaranUiState.Success(pengeluaranRepository.getPengeluaran().data)
            } catch (e: IOException) {
                HomePengeluaranUiState.Error
            } catch (e: HttpException) {
                HomePengeluaranUiState.Error
            }
        }
    }

    fun deletePengeluaran(idPengeluaran: String) {
        viewModelScope.launch {
            try {
                pengeluaranRepository.deletePengeluaran(idPengeluaran)
            } catch (e: IOException) {
                HomePengeluaranUiState.Error
            } catch (e: HttpException) {
                HomePengeluaranUiState.Error
            }
        }
    }
}
