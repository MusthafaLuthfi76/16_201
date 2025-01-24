package com.example.final_pam.repository

import com.example.final_pam.model.AllPendapatanResponse
import com.example.final_pam.model.Pendapatan
import com.example.final_pam.service.PendapatanService
import okio.IOException

interface PendapatanRepository {
    suspend fun getPendapatan(): AllPendapatanResponse
    suspend fun insertPendapatan(pendapatan: Pendapatan)
    suspend fun updatePendapatan(idPendapatan: Int, pendapatan: Pendapatan) // idPendapatan menjadi Int
    suspend fun deletePendapatan(idPendapatan: Int) // idPendapatan menjadi Int
    suspend fun getPendapatanById(idPendapatan: Int): Pendapatan // idPendapatan menjadi Int
}

class NetworkPendapatanRepository(
    private val pendapatanApiService: PendapatanService
) : PendapatanRepository {

    override suspend fun insertPendapatan(pendapatan: Pendapatan) {
        pendapatanApiService.insertPendapatan(pendapatan)
    }

    override suspend fun updatePendapatan(idPendapatan: Int, pendapatan: Pendapatan) { // idPendapatan menjadi Int
        pendapatanApiService.updatePendapatan(idPendapatan, pendapatan)
    }

    override suspend fun deletePendapatan(idPendapatan: Int) { // idPendapatan menjadi Int
        try {
            val response = pendapatanApiService.deletePendapatan(idPendapatan)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Pendapatan. HTTP Status code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPendapatan(): AllPendapatanResponse = pendapatanApiService.getAllPendapatan()

    override suspend fun getPendapatanById(idPendapatan: Int): Pendapatan { // idPendapatan menjadi Int
        return pendapatanApiService.getPendapatanById(idPendapatan).data
    }
}
