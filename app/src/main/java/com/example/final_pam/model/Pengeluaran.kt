package com.example.final_pam.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pengeluaran(
    @SerialName("Id_pengeluaran")
    val idPengeluaran: String,

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
data class AllPengeluaranResponse(
    val status: Boolean,
    val message: String,
    val data: List<Pengeluaran>
)

@Serializable
data class PengeluaranDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Pengeluaran
)