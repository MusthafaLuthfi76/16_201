package com.example.final_pam.repository

import com.example.final_pam.model.AllAsetResponse
import com.example.final_pam.model.Aset
import com.example.final_pam.service.AsetService
import java.io.IOException

interface AsetRepository {
    suspend fun getAset(): AllAsetResponse
    suspend fun insertAset(aset: Aset)
    suspend fun updateAset(idAset: String, aset: Aset) // idAset sebagai String
    suspend fun deleteAset(idAset: String) // idAset sebagai String
    suspend fun getAsetById(idAset: String): Aset // idAset sebagai String
}

class NetworkAsetRepository(
    private val asetApiService: AsetService
) : AsetRepository {

    override suspend fun insertAset(aset: Aset) {
        asetApiService.insertAset(aset)
    }

    override suspend fun updateAset(idAset: String, aset: Aset) {
        asetApiService.updateAset(idAset, aset)
    }

    override suspend fun deleteAset(idAset: String) {
        try {
            val response = asetApiService.deleteAset(idAset)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Aset. HTTP Status code: ${response.code()}")
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getAset(): AllAsetResponse = asetApiService.getAllAset()

    override suspend fun getAsetById(idAset: String): Aset {
        return asetApiService.getAsetById(idAset).data
    }
}

