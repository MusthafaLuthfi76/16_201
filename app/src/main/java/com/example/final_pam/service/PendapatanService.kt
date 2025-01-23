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
    @GET(".")
    suspend fun getAllPendapatan(): AllPendapatanResponse

    @GET("{id_pendapatan}")
    suspend fun getPendapatanById(@Path("id_pendapatan") idPendapatan: String): PendapatanDetailResponse

    @POST("store")
    suspend fun insertPendapatan(@Body pendapatan: Pendapatan)

    @PUT("{id_pendapatan}")
    suspend fun updatePendapatan(@Path("id_pendapatan") idPendapatan: String, @Body pendapatan: Pendapatan)

    @DELETE("{id_pendapatan}")
    suspend fun deletePendapatan(@Path("id_pendapatan") idPendapatan: String): retrofit2.Response<Void>
}
