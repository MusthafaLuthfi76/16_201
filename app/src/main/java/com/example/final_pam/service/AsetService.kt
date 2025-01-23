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
    suspend fun getAsetById(@Path("id_aset") idAset: String): AsetDetailResponse

    @POST("store")
    suspend fun insertAset(@Body aset: Aset)

    @PUT("{id_aset}")
    suspend fun updateAset(@Path("id_aset") idAset: String, @Body aset: Aset)

    @DELETE("{id_aset}")
    suspend fun deleteAset(@Path("id_aset") idAset: String): retrofit2.Response<Void>
}
