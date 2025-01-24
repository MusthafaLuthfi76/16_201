package com.example.final_pam.repository

import com.example.final_pam.model.AllKategoriResponse
import com.example.final_pam.model.Kategori
import com.example.final_pam.service.KategoriService
import okio.IOException

interface KategoriRepository {
    suspend fun getKategori(): AllKategoriResponse
    suspend fun insertKategori(kategori: Kategori)
    suspend fun updateKategori(idKategori: Int, kategori: Kategori) // idKategori menjadi Int
    suspend fun deleteKategori(idKategori: Int) // idKategori menjadi Int
    suspend fun getKategoriById(idKategori: Int): Kategori // idKategori menjadi Int
}

class NetworkKategoriRepository(
    private val kategoriApiService: KategoriService
) : KategoriRepository {

    override suspend fun insertKategori(kategori: Kategori) {
        kategoriApiService.insertKategori(kategori)
    }

    override suspend fun updateKategori(idKategori: Int, kategori: Kategori) { // idKategori menjadi Int
        kategoriApiService.updateKategori(idKategori, kategori)
    }

    override suspend fun deleteKategori(idKategori: Int) { // idKategori menjadi Int
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

    override suspend fun getKategoriById(idKategori: Int): Kategori { // idKategori menjadi Int
        return kategoriApiService.getKategoriById(idKategori).data
    }
}
