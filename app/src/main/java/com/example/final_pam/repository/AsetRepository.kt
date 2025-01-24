package com.example.final_pam.repository

import com.example.final_pam.model.AllAsetResponse
import com.example.final_pam.model.Aset
import com.example.final_pam.service.AsetService
import java.io.IOException

interface AsetRepository {
    suspend fun getAset(): AllAsetResponse
    suspend fun insertAset(aset: Aset)
    suspend fun updateAset(idAset: Int, aset: Aset) // idAset sebagai Int
    suspend fun deleteAset(idAset: Int) // idAset sebagai Int
    suspend fun getAsetById(idAset: Int): Aset // idAset sebagai Int
}

class NetworkAsetRepository(
    private val asetApiService: AsetService
) : AsetRepository {

    override suspend fun insertAset(aset: Aset) {
        asetApiService.insertAset(aset)
    }

    override suspend fun updateAset(idAset: Int, aset: Aset) {
        asetApiService.updateAset(idAset, aset) // idAset sebagai Int
    }

    override suspend fun deleteAset(idAset: Int) { // idAset sebagai Int
        try {
            val response = asetApiService.deleteAset(idAset)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Aset. HTTP Status code: ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getAset(): AllAsetResponse = asetApiService.getAllAset()

    override suspend fun getAsetById(idAset: Int): Aset { // idAset sebagai Int
        return asetApiService.getAsetById(idAset).data
    }
}
