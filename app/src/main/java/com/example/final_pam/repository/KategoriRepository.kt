package com.example.final_pam.repository

import com.example.final_pam.model.AllKategoriResponse
import com.example.final_pam.model.Kategori
import com.example.final_pam.service.KategoriService
import okio.IOException

interface KategoriRepository {
    suspend fun getKategori(): AllKategoriResponse
    suspend fun insertKategori(kategori: Kategori)
    suspend fun updateKategori(idKategori: String, kategori: Kategori)
    suspend fun deleteKategori(idKategori: String)
    suspend fun getKategoriById(idKategori: String): Kategori
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
                throw IOException("Failed to delete Kategori. HTTP Status code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
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
