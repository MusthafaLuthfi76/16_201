package com.example.final_pam.ui.viewmodel.kategori

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_pam.model.Kategori
import com.example.final_pam.repository.KategoriRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class KategoriHomeUiState {
    data class Success(val kategori: List<Kategori>) : KategoriHomeUiState()
    object Error : KategoriHomeUiState()
    object Loading : KategoriHomeUiState()
}

class KategoriHomeViewModel(private val kategoriRepository: KategoriRepository) : ViewModel() {
    var kategoriUIState: KategoriHomeUiState by mutableStateOf(KategoriHomeUiState.Loading)
        private set

    init {
        getKategori()
    }

    fun getKategori() {
        viewModelScope.launch {
            kategoriUIState = KategoriHomeUiState.Loading
            kategoriUIState = try {
                KategoriHomeUiState.Success(kategoriRepository.getKategori().data)
            } catch (e: IOException) {
                KategoriHomeUiState.Error
            } catch (e: HttpException) {
                KategoriHomeUiState.Error
            }
        }
    }

    fun deleteKategori(idKategori: String) {
        viewModelScope.launch {
            try {
                kategoriRepository.deleteKategori(idKategori)
                getKategori() // Refresh data setelah penghapusan
            } catch (e: IOException) {
                kategoriUIState = KategoriHomeUiState.Error
            } catch (e: HttpException) {
                kategoriUIState = KategoriHomeUiState.Error
            }
        }
    }
}

