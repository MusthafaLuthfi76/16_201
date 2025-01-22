package com.example.final_pam.application

import android.app.Application

class KeuanganApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AsetContainer() // Menggunakan AsetContainer untuk mengatur dependensi terkait aset
    }
}