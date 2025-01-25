package com.example.final_pam.service

import com.example.final_pam.model.AllPendapatanResponse
import com.example.final_pam.model.Pendapatan
import com.example.final_pam.model.PendapatanDetailResponse
import retrofit2.http.*

interface PendapatanService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("pendapatan/")
    suspend fun getAllPendapatan(): AllPendapatanResponse

    @GET("pendapatan/{id_pendapatan}")
    suspend fun getPendapatanById(@Path("id_pendapatan") idPendapatan: String): PendapatanDetailResponse // idPendapatan sebagai String

    @POST("pendapatan/store")
    suspend fun insertPendapatan(@Body pendapatan: Pendapatan)

    @PUT("pendapatan/{id_pendapatan}")
    suspend fun updatePendapatan(@Path("id_pendapatan") idPendapatan: String, @Body pendapatan: Pendapatan) // idPendapatan sebagai String

    @DELETE("pendapatan/{id_pendapatan}")
    suspend fun deletePendapatan(@Path("id_pendapatan") idPendapatan: String): retrofit2.Response<Void> // idPendapatan sebagai String
}

