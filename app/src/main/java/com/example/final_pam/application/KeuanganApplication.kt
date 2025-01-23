package com.example.final_pam.application

import android.app.Application
import com.example.final_pam.dependenciesinjection.AppContainer
import com.example.final_pam.dependenciesinjection.KeuanganContainer

class KeuanganApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = KeuanganContainer() // Menggunakan KeuanganContainer untuk mengatur dependensi terkait aset
    }
}