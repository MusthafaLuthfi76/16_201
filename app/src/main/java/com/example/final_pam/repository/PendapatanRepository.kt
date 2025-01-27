package com.example.final_pam.repository

import com.example.final_pam.model.AllPendapatanResponse
import com.example.final_pam.model.Pendapatan
import com.example.final_pam.service.PendapatanService
import java.io.IOException

interface PendapatanRepository {
    suspend fun getPendapatan(): AllPendapatanResponse
    suspend fun insertPendapatan(pendapatan: Pendapatan)
    suspend fun updatePendapatan(idPendapatan: String, pendapatan: Pendapatan) // idPendapatan sebagai String
    suspend fun deletePendapatan(idPendapatan: String) // idPendapatan sebagai String
    suspend fun getPendapatanById(idPendapatan: String): Pendapatan // idPendapatan sebagai String
}

class NetworkPendapatanRepository(
    private val pendapatanApiService: PendapatanService
) : PendapatanRepository {

    override suspend fun insertPendapatan(pendapatan: Pendapatan) {
        pendapatanApiService.insertPendapatan(pendapatan)
    }

    override suspend fun updatePendapatan(idPendapatan: String, pendapatan: Pendapatan) {
        pendapatanApiService.updatePendapatan(idPendapatan, pendapatan)
    }

    override suspend fun deletePendapatan(idPendapatan: String) {
        try {
            val response = pendapatanApiService.deletePendapatan(idPendapatan)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Pendapatan. HTTP Status code: ${response.code()}")
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPendapatan(): AllPendapatanResponse = pendapatanApiService.getAllPendapatan()

    override suspend fun getPendapatanById(idPendapatan: String): Pendapatan {
        return pendapatanApiService.getPendapatanById(idPendapatan).data
    }
}


