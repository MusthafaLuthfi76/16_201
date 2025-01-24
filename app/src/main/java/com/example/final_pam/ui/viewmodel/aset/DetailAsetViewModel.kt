package com.example.final_pam.ui.viewmodel.aset

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_pam.model.Aset
import com.example.final_pam.repository.AsetRepository
import com.example.final_pam.ui.view.DestinasiDetailAset
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class DetailAsetUiState {
    data class Success(val aset: Aset) : DetailAsetUiState()
    object Error : DetailAsetUiState()
    object Loading : DetailAsetUiState()
}

class DetailAsetViewModel(
    savedStateHandle: SavedStateHandle,
    private val asetRepository: AsetRepository
) : ViewModel() {

    var asetDetailState: DetailAsetUiState by mutableStateOf(DetailAsetUiState.Loading)
        private set

    private val _idAset: Int = checkNotNull(savedStateHandle[DestinasiDetailAset.idAset])

    init {
        getAsetById()
    }

    fun getAsetById() {
        viewModelScope.launch {
            asetDetailState = DetailAsetUiState.Loading
            asetDetailState = try {
                val aset = asetRepository.getAsetById(_idAset)
                DetailAsetUiState.Success(aset)
            } catch (e: IOException) {
                DetailAsetUiState.Error
            } catch (e: HttpException) {
                DetailAsetUiState.Error
            }
        }
    }

    fun deleteAset(idAset: Int) {
        viewModelScope.launch {
            try {
                asetRepository.deleteAset(idAset)
            } catch (e: IOException) {
                DetailAsetUiState.Error
            } catch (e: HttpException) {
                DetailAsetUiState.Error
            }
        }
    }
}
