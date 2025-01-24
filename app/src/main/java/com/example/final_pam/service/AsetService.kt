package com.example.final_pam.service

import com.example.final_pam.model.AllAsetResponse
import com.example.final_pam.model.Aset
import com.example.final_pam.model.AsetDetailResponse
import retrofit2.http.*

interface AsetService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET(".")
    suspend fun getAllAset(): AllAsetResponse

    @GET("{id_aset}")
    suspend fun getAsetById(@Path("id_aset") idAset: Int): AsetDetailResponse // idAset sebagai Int

    @POST("store")
    suspend fun insertAset(@Body aset: Aset)

    @PUT("{id_aset}")
    suspend fun updateAset(@Path("id_aset") idAset: Int, @Body aset: Aset) // idAset sebagai Int

    @DELETE("{id_aset}")
    suspend fun deleteAset(@Path("id_aset") idAset: Int): retrofit2.Response<Void> // idAset sebagai Int
}
