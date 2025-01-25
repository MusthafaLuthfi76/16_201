package com.example.final_pam.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Aset(
    @SerialName("Id_aset")
    val Id_Aset: String,

    @SerialName("Nama_aset")
    val Nama_aset: String
)

@Serializable
data class AllAsetResponse(
    val status: Boolean,
    val message: String,
    val data: List<Aset>
)

@Serializable
data class AsetDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Aset
)
