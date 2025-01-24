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

sealed class DetailPengeluaranUiState {
    data class Success(val pengeluaran: Pengeluaran) : DetailPengeluaranUiState()
    object Error : DetailPengeluaranUiState()
    object Loading : DetailPengeluaranUiState()
}

class DetailPengeluaranViewModel(
    savedStateHandle: SavedStateHandle,
    private val pengeluaranRepository: PengeluaranRepository
) : ViewModel() {

    var pengeluaranDetailState: DetailPengeluaranUiState by mutableStateOf(DetailPengeluaranUiState.Loading)
        private set

    private val _idPengeluaran: Int = checkNotNull(savedStateHandle[DestinasiDetailPengeluaran.ID_PENGELUARAN])

    init {
        getPengeluaranById()
    }

    fun getPengeluaranById() {
        viewModelScope.launch {
            pengeluaranDetailState = DetailPengeluaranUiState.Loading
            pengeluaranDetailState = try {
                val pengeluaran = pengeluaranRepository.getPengeluaranById(_idPengeluaran)
                DetailPengeluaranUiState.Success(pengeluaran)
            } catch (e: IOException) {
                DetailPengeluaranUiState.Error
            } catch (e: HttpException) {
                DetailPengeluaranUiState.Error
            }
        }
    }

    fun deletePengeluaran(idPengeluaran: Int) {
        viewModelScope.launch {
            try {
                pengeluaranRepository.deletePengeluaran(idPengeluaran)
            } catch (e: IOException) {
                DetailPengeluaranUiState.Error
            } catch (e: HttpException) {
                DetailPengeluaranUiState.Error
            }
        }
    }
}
