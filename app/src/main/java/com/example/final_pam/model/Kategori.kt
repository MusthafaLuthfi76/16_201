package com.example.final_pam.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Kategori(
    @SerialName("Id_kategori")
    val idKategori: String,

    @SerialName("Nama_kategori")
    val namaKategori: String
)

@Serializable
data class AllKategoriResponse(
    val status: Boolean,
    val message: String,
    val data: List<Kategori>
)

@Serializable
data class KategoriDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Kategori
)
