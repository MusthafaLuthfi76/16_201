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

sealed class DetailKategoriUiState {
    data class Success(val kategori: Kategori) : DetailKategoriUiState()
    object Error : DetailKategoriUiState()
    object Loading : DetailKategoriUiState()
}

class DetailKategoriViewModel(
    savedStateHandle: SavedStateHandle,
    private val kategoriRepository: KategoriRepository
) : ViewModel() {

    var kategoriDetailState: DetailKategoriUiState by mutableStateOf(DetailKategoriUiState.Loading)
        private set

    private val _idKategori: Int = checkNotNull(savedStateHandle["id_kategori"])

    init {
        getKategoriById()
    }

    fun getKategoriById() {
        viewModelScope.launch {
            kategoriDetailState = DetailKategoriUiState.Loading
            kategoriDetailState = try {
                val kategori = kategoriRepository.getKategoriById(_idKategori)
                DetailKategoriUiState.Success(kategori)
            } catch (e: IOException) {
                DetailKategoriUiState.Error
            } catch (e: HttpException) {
                DetailKategoriUiState.Error
            }
        }
    }

    fun deleteKategori(idKategori: Int) {
        viewModelScope.launch {
            try {
                kategoriRepository.deleteKategori(idKategori)
            } catch (e: IOException) {
                DetailKategoriUiState.Error
            } catch (e: HttpException) {
                DetailKategoriUiState.Error
            }
        }
    }
}
