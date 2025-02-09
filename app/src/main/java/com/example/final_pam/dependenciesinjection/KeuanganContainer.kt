package com.example.final_pam.dependenciesinjection
import com.example.final_pam.repository.AsetRepository
import com.example.final_pam.repository.KategoriRepository
import com.example.final_pam.repository.NetworkAsetRepository
import com.example.final_pam.repository.NetworkKategoriRepository
import com.example.final_pam.repository.NetworkPendapatanRepository
import com.example.final_pam.repository.NetworkPengeluaranRepository
import com.example.final_pam.repository.PendapatanRepository
import com.example.final_pam.repository.PengeluaranRepository
import com.example.final_pam.service.AsetService
import com.example.final_pam.service.KategoriService
import com.example.final_pam.service.PendapatanService
import com.example.final_pam.service.PengeluaranService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

// AppContainer akan menampung semua repository yang dibutuhkan oleh aplikasi
interface AppContainer {
    val asetRepository: AsetRepository
    val kategoriRepository: KategoriRepository
    val pendapatanRepository: PendapatanRepository
    val pengeluaranRepository: PengeluaranRepository
}

// AsetContainer akan mengimplementasikan AppContainer untuk menyediakan dependensi
class KeuanganContainer : AppContainer {
    private val baseUrl = "http://10.0.2.2:3000/api/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    // Services
    private val asetService: AsetService by lazy {
        retrofit.create(AsetService::class.java)
    }

    private val kategoriService: KategoriService by lazy {
        retrofit.create(KategoriService::class.java)
    }

    private val pendapatanService: PendapatanService by lazy {
        retrofit.create(PendapatanService::class.java)
    }

    private val pengeluaranService: PengeluaranService by lazy {
        retrofit.create(PengeluaranService::class.java)
    }

    // Repositories
    override val asetRepository: AsetRepository by lazy {
        NetworkAsetRepository(asetService)
    }

    override val kategoriRepository: KategoriRepository by lazy {
        NetworkKategoriRepository(kategoriService)
    }

    override val pendapatanRepository: PendapatanRepository by lazy {
        NetworkPendapatanRepository(pendapatanService)
    }

    override val pengeluaranRepository: PengeluaranRepository by lazy {
        NetworkPengeluaranRepository(pengeluaranService)
    }
}
