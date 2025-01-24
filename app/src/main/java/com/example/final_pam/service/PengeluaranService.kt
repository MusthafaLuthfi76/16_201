package com.example.final_pam.service

import com.example.final_pam.model.AllPengeluaranResponse
import com.example.final_pam.model.Pengeluaran
import com.example.final_pam.model.PengeluaranDetailResponse
import retrofit2.http.*

interface PengeluaranService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET(".")
    suspend fun getAllPengeluaran(): AllPengeluaranResponse

    @GET("{id_pengeluaran}")
    suspend fun getPengeluaranById(@Path("id_pengeluaran") idPengeluaran: Int): PengeluaranDetailResponse // Ubah idPengeluaran menjadi Int

    @POST("store")
    suspend fun insertPengeluaran(@Body pengeluaran: Pengeluaran)

    @PUT("{id_pengeluaran}")
    suspend fun updatePengeluaran(@Path("id_pengeluaran") idPengeluaran: Int, @Body pengeluaran: Pengeluaran) // Ubah idPengeluaran menjadi Int

    @DELETE("{id_pengeluaran}")
    suspend fun deletePengeluaran(@Path("id_pengeluaran") idPengeluaran: Int): retrofit2.Response<Void> // Ubah idPengeluaran menjadi Int
}
