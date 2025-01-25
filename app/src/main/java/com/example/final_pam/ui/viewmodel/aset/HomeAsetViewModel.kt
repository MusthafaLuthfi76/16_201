package com.example.final_pam.ui.viewmodel.aset

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_pam.model.Aset
import com.example.final_pam.repository.AsetRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class AsetHomeUiState {
    data class Success(val aset: List<Aset>) : AsetHomeUiState()
    object Error : AsetHomeUiState()
    object Loading : AsetHomeUiState()
}

class AsetHomeViewModel(private val asetRepository: AsetRepository) : ViewModel() {
    var asetUIState: AsetHomeUiState by mutableStateOf(AsetHomeUiState.Loading)
        private set

    init {
        getAset()
    }

    fun getAset() {
        viewModelScope.launch {
            asetUIState = AsetHomeUiState.Loading
            asetUIState = try {
                AsetHomeUiState.Success(asetRepository.getAset().data)
            } catch (e: IOException) {
                AsetHomeUiState.Error
            } catch (e: HttpException) {
                AsetHomeUiState.Error
            }
        }
    }

    fun deleteAset(idAset: String) {
        viewModelScope.launch {
            try {
                asetRepository.deleteAset(idAset)
                getAset() // Refresh data setelah penghapusan
            } catch (e: IOException) {
                asetUIState = AsetHomeUiState.Error
            } catch (e: HttpException) {
                asetUIState = AsetHomeUiState.Error
            }
        }
    }
}

