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
    @GET("aset/")
    suspend fun getAllAset(): AllAsetResponse

    @GET("aset/{id_aset}")
    suspend fun getAsetById(@Path("id_aset") idAset: String): AsetDetailResponse // idAset sebagai String

    @POST("aset/store")
    suspend fun insertAset(@Body aset: Aset)

    @PUT("aset/{id_aset}")
    suspend fun updateAset(@Path("id_aset") idAset: String, @Body aset: Aset) // idAset sebagai String

    @DELETE("aset/{id_aset}")
    suspend fun deleteAset(@Path("id_aset") idAset: String): retrofit2.Response<Void> // idAset sebagai String
}
