package com.example.final_pam.model

import com.example.session12.model.Mahasiswa
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Aset(
    @SerialName("Id_aset")
    val idAset: String,

    @SerialName("Nama_aset")
    val namaAset: String
)

@Serializable
data class AllAsetResponse(
    val status: Boolean,
    val message: String,
    val data: List<Mahasiswa>
)

@Serializable
data class AsetDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Mahasiswa
)
