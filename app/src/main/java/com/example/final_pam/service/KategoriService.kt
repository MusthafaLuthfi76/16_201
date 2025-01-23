package com.example.final_pam.service

import com.example.final_pam.model.AllKategoriResponse
import com.example.final_pam.model.Kategori
import com.example.final_pam.model.KategoriDetailResponse
import retrofit2.http.*

interface KategoriService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET(".")
    suspend fun getAllKategori(): AllKategoriResponse

    @GET("{id_kategori}")
    suspend fun getKategoriById(@Path("id_kategori") idKategori: String): KategoriDetailResponse

    @POST("store")
    suspend fun insertKategori(@Body kategori: Kategori)

    @PUT("{id_kategori}")
    suspend fun updateKategori(@Path("id_kategori") idKategori: String, @Body kategori: Kategori)

    @DELETE("{id_kategori}")
    suspend fun deleteKategori(@Path("id_kategori") idKategori: String): retrofit2.Response<Void>
}
