package com.example.final_pam.repository

import com.example.final_pam.model.AllPengeluaranResponse
import com.example.final_pam.model.Pengeluaran
import com.example.final_pam.service.PengeluaranService
import okio.IOException

interface PengeluaranRepository {
    suspend fun getPengeluaran(): AllPengeluaranResponse
    suspend fun insertPengeluaran(pengeluaran: Pengeluaran)
    suspend fun updatePengeluaran(idPengeluaran: Int, pengeluaran: Pengeluaran) // Ubah idPengeluaran menjadi Int
    suspend fun deletePengeluaran(idPengeluaran: Int) // Ubah idPengeluaran menjadi Int
    suspend fun getPengeluaranById(idPengeluaran: Int): Pengeluaran // Ubah idPengeluaran menjadi Int
}

class NetworkPengeluaranRepository(
    private val pengeluaranApiService: PengeluaranService
) : PengeluaranRepository {

    override suspend fun insertPengeluaran(pengeluaran: Pengeluaran) {
        pengeluaranApiService.insertPengeluaran(pengeluaran)
    }

    override suspend fun updatePengeluaran(idPengeluaran: Int, pengeluaran: Pengeluaran) { // Ubah idPengeluaran menjadi Int
        pengeluaranApiService.updatePengeluaran(idPengeluaran, pengeluaran)
    }

    override suspend fun deletePengeluaran(idPengeluaran: Int) { // Ubah idPengeluaran menjadi Int
        try {
            val response = pengeluaranApiService.deletePengeluaran(idPengeluaran)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Pengeluaran. HTTP Status code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPengeluaran(): AllPengeluaranResponse = pengeluaranApiService.getAllPengeluaran()

    override suspend fun getPengeluaranById(idPengeluaran: Int): Pengeluaran { // Ubah idPengeluaran menjadi Int
        return pengeluaranApiService.getPengeluaranById(idPengeluaran).data
    }
}
