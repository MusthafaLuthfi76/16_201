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

sealed class HomeAsetUiState {
    data class Success(val aset: List<Aset>) : HomeAsetUiState()
    object Error : HomeAsetUiState()
    object Loading : HomeAsetUiState()
}

class HomeAsetViewModel(private val asetRepository: AsetRepository) : ViewModel() {
    var asetUIState: HomeAsetUiState by mutableStateOf(HomeAsetUiState.Loading)
        private set

    init {
        getAset()
    }

    fun getAset() {
        viewModelScope.launch {
            asetUIState = HomeAsetUiState.Loading
            asetUIState = try {
                HomeAsetUiState.Success(asetRepository.getAset().data)
            } catch (e: IOException) {
                HomeAsetUiState.Error
            } catch (e: HttpException) {
                HomeAsetUiState.Error
            }
        }
    }

    fun deleteAset(idAset: String) {
        viewModelScope.launch {
            try {
                asetRepository.deleteAset(idAset)
            } catch (e: IOException) {
                HomeAsetUiState.Error
            } catch (e: HttpException) {
                HomeAsetUiState.Error
            }
        }
    }
}
