package com.example.final_pam.ui.viewmodel.kategori

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_pam.model.Kategori
import com.example.final_pam.repository.KategoriRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class KategoriDetailUiState {
    data class Success(val kategori: Kategori) : KategoriDetailUiState()
    object Error : KategoriDetailUiState()
    object Loading : KategoriDetailUiState()
}

class KategoriDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val kategoriRepository: KategoriRepository
) : ViewModel() {

    var kategoriDetailState: KategoriDetailUiState by mutableStateOf(KategoriDetailUiState.Loading)
        private set

    private val _idKategori: String = checkNotNull(savedStateHandle["id_kategori"])

    init {
        getKategoriById()
    }

    fun getKategoriById() {
        viewModelScope.launch {
            kategoriDetailState = KategoriDetailUiState.Loading
            kategoriDetailState = try {
                val kategori = kategoriRepository.getKategoriById(_idKategori)
                KategoriDetailUiState.Success(kategori)
            } catch (e: IOException) {
                KategoriDetailUiState.Error
            } catch (e: HttpException) {
                KategoriDetailUiState.Error
            }
        }
    }

    fun deleteKategori(idKategori: String) {
        viewModelScope.launch {
            try {
                kategoriRepository.deleteKategori(idKategori)
            } catch (e: IOException) {
                kategoriDetailState = KategoriDetailUiState.Error
            } catch (e: HttpException) {
                kategoriDetailState = KategoriDetailUiState.Error
            }
        }
    }
}

