package com.example.final_pam.repository

import com.example.final_pam.model.AllPengeluaranResponse
import com.example.final_pam.model.Pengeluaran
import com.example.final_pam.service.PengeluaranService
import okio.IOException

interface PengeluaranRepository {
    suspend fun getPengeluaran(): AllPengeluaranResponse
    suspend fun insertPengeluaran(pengeluaran: Pengeluaran)
    suspend fun updatePengeluaran(idPengeluaran: String, pengeluaran: Pengeluaran) // idPengeluaran sebagai String
    suspend fun deletePengeluaran(idPengeluaran: String) // idPengeluaran sebagai String
    suspend fun getPengeluaranById(idPengeluaran: String): Pengeluaran // idPengeluaran sebagai String
}

class NetworkPengeluaranRepository(
    private val pengeluaranApiService: PengeluaranService
) : PengeluaranRepository {

    override suspend fun insertPengeluaran(pengeluaran: Pengeluaran) {
        pengeluaranApiService.insertPengeluaran(pengeluaran)
    }

    override suspend fun updatePengeluaran(idPengeluaran: String, pengeluaran: Pengeluaran) {
        pengeluaranApiService.updatePengeluaran(idPengeluaran, pengeluaran)
    }

    override suspend fun deletePengeluaran(idPengeluaran: String) {
        try {
            val response = pengeluaranApiService.deletePengeluaran(idPengeluaran)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Pengeluaran. HTTP Status code: ${response.code()}")
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPengeluaran(): AllPengeluaranResponse = pengeluaranApiService.getAllPengeluaran()

    override suspend fun getPengeluaranById(idPengeluaran: String): Pengeluaran {
        return pengeluaranApiService.getPengeluaranById(idPengeluaran).data
    }
}

