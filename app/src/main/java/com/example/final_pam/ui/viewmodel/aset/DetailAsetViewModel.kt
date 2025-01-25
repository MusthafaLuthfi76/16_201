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

sealed class AsetDetailUiState {
    data class Success(val aset: Aset) : AsetDetailUiState()
    object Error : AsetDetailUiState()
    object Loading : AsetDetailUiState()
}

class AsetDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val asetRepository: AsetRepository
) : ViewModel() {

    var asetDetailState: AsetDetailUiState by mutableStateOf(AsetDetailUiState.Loading)
        private set

    private val _idAset: String = savedStateHandle[DestinasiDetailAset.ID_ASET]
        ?: throw IllegalArgumentException("Id_Aset tidak ditemukan atau null")



    init {
        getAsetById()
    }

    fun getAsetById() {
        viewModelScope.launch {
            asetDetailState = AsetDetailUiState.Loading
            asetDetailState = try {
                val aset = asetRepository.getAsetById(_idAset)
                AsetDetailUiState.Success(aset)
            } catch (e: IOException) {
                AsetDetailUiState.Error
            } catch (e: HttpException) {
                AsetDetailUiState.Error
            }
        }
    }

    fun deleteAset(idAset: String) {
        viewModelScope.launch {
            try {
                asetRepository.deleteAset(idAset)
            } catch (e: IOException) {
                asetDetailState = AsetDetailUiState.Error
            } catch (e: HttpException) {
                asetDetailState = AsetDetailUiState.Error
            }
        }
    }
}

