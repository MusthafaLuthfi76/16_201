package com.example.final_pam.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pendapatan(
    @SerialName("Id_pendapatan")
    val Id_Pendapatan: String,

    @SerialName("Id_aset")
    val Id_Aset: String,

    @SerialName("Id_kategori")
    val Id_Kategori: String,

    @SerialName("Tanggal_transaksi")
    val tglTransaksi: String,

    @SerialName("total")
    val total: Int,

    @SerialName("catatan")
    val catatan: String,
)

@Serializable
data class AllPendapatanResponse(
    val status: Boolean,
    val message: String,
    val data: List<Pendapatan>
)

@Serializable
data class PendapatanDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Pendapatan
)