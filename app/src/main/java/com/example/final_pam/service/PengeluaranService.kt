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
    @GET("pengeluaran/")
    suspend fun getAllPengeluaran(): AllPengeluaranResponse

    @GET("pengeluaran/{id_pengeluaran}")
    suspend fun getPengeluaranById(@Path("id_pengeluaran") idPengeluaran: String): PengeluaranDetailResponse // idPengeluaran sebagai String

    @POST("pengeluaran/store")
    suspend fun insertPengeluaran(@Body pengeluaran: Pengeluaran)

    @PUT("pengeluaran/{id_pengeluaran}")
    suspend fun updatePengeluaran(@Path("id_pengeluaran") idPengeluaran: String, @Body pengeluaran: Pengeluaran) // idPengeluaran sebagai String

    @DELETE("pengeluaran/{id_pengeluaran}")
    suspend fun deletePengeluaran(@Path("id_pengeluaran") idPengeluaran: String): retrofit2.Response<Void> // idPengeluaran sebagai String
}

