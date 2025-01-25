package com.example.final_pam.repository

import com.example.final_pam.model.AllKategoriResponse
import com.example.final_pam.model.Kategori
import com.example.final_pam.service.KategoriService
import okio.IOException

interface KategoriRepository {
    suspend fun getKategori(): AllKategoriResponse
    suspend fun insertKategori(kategori: Kategori)
    suspend fun updateKategori(idKategori: String, kategori: Kategori) // idKategori sebagai String
    suspend fun deleteKategori(idKategori: String) // idKategori sebagai String
    suspend fun getKategoriById(idKategori: String): Kategori // idKategori sebagai String
}

class NetworkKategoriRepository(
    private val kategoriApiService: KategoriService
) : KategoriRepository {

    override suspend fun insertKategori(kategori: Kategori) {
        kategoriApiService.insertKategori(kategori)
    }

    override suspend fun updateKategori(idKategori: String, kategori: Kategori) {
        kategoriApiService.updateKategori(idKategori, kategori)
    }

    override suspend fun deleteKategori(idKategori: String) {
        try {
            val response = kategoriApiService.deleteKategori(idKategori)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Kategori. HTTP Status code: ${response.code()}")
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getKategori(): AllKategoriResponse = kategoriApiService.getAllKategori()

    override suspend fun getKategoriById(idKategori: String): Kategori {
        return kategoriApiService.getKategoriById(idKategori).data
    }
}

