package com.example.final_pam.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.sql.Date

@Serializable
data class Pengeluaran(
    @SerialName("Id_pengeluaran")
    val idPengeluaran: Int,

    @SerialName("Id_aset")
    val idAset: Int,

    @SerialName("Id_kategori")
    val idKategori: Int,

    @SerialName("Tanggal_transaksi")
    val tglTransaksi: String,

    @SerialName("total")
    val total: Int,

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