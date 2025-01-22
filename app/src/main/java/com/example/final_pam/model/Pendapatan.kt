package com.example.final_pam.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pendapatan(
    @SerialName("Id_pendapatan")
    val idPendapatan: String,

    @SerialName("Id_aset")
    val idAset: String,

    @SerialName("Id_kategori")
    val idKategori: String,

    @SerialName("Tanggal_transaksi")
    val tglTransaksi: String,

    @SerialName("total")
    val total: String,

    @SerialName("catatan")
    val catatan: String,
)

@Serializable
data class AllPendapatanResponse(
    val status: Boolean,
    val message: String,
    val data: List<Aset>
)

@Serializable
data class PendapatanDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Aset
)