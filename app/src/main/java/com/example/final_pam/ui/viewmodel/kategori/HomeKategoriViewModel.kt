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

sealed class HomeKategoriUiState {
    data class Success(val kategori: List<Kategori>) : HomeKategoriUiState()
    object Error : HomeKategoriUiState()
    object Loading : HomeKategoriUiState()
}

class HomeKategoriViewModel(private val kategoriRepository: KategoriRepository) : ViewModel() {
    var kategoriUIState: HomeKategoriUiState by mutableStateOf(HomeKategoriUiState.Loading)
        private set

    init {
        getKategori()
    }

    fun getKategori() {
        viewModelScope.launch {
            kategoriUIState = HomeKategoriUiState.Loading
            kategoriUIState = try {
                HomeKategoriUiState.Success(kategoriRepository.getKategori().data)
            } catch (e: IOException) {
                HomeKategoriUiState.Error
            } catch (e: HttpException) {
                HomeKategoriUiState.Error
            }
        }
    }

    fun deleteKategori(idKategori: Int) {
        viewModelScope.launch {
            try {
                kategoriRepository.deleteKategori(idKategori)
            } catch (e: IOException) {
                HomeKategoriUiState.Error
            } catch (e: HttpException) {
                HomeKategoriUiState.Error
            }
        }
    }
}
