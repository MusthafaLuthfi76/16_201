package com.example.final_pam.data

import com.example.final_pam.repository.KategoriRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

object KategoriDD {
    private val _options = MutableStateFlow<List<String>>(emptyList()) // List dengan format "Id_kategori: Nama_kategori"
    val options: StateFlow<List<String>> = _options

    // Fungsi untuk memuat data dari server
    fun loadData(kategoriRepository: KategoriRepository) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Memanggil data kategori dari repository
                val response = kategoriRepository.getKategori()
                if (response.status) {
                    // Mengubah data menjadi format "Id_kategori: Nama_kategori"
                    _options.value = response.data.map { kategori ->
                        "${kategori.idKategori}: ${kategori.namaKategori}"
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
