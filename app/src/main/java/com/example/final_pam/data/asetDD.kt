package com.example.final_pam.data

import com.example.final_pam.model.Aset
import com.example.final_pam.repository.AsetRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

object Aset {
    private val _options = MutableStateFlow<List<String>>(emptyList()) // List dengan format "Id_aset: Nama_aset"
    val options: StateFlow<List<String>> = _options

    // Fungsi untuk memuat data dari server
    fun loadData(asetRepository: AsetRepository) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Memanggil data aset dari repository
                val response = asetRepository.getAset()
                if (response.status) {
                    // Mengubah data menjadi format "Id_aset: Nama_aset"
                    _options.value = response.data.map { aset ->
                        "${aset.Id_Aset}: ${aset.Nama_aset}"
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
