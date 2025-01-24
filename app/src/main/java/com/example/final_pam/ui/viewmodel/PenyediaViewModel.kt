package com.example.final_pam.ui.viewmodel

import UpdateAsetViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.final_pam.application.KeuanganApplication
import com.example.final_pam.ui.viewmodel.aset.DetailAsetViewModel
import com.example.final_pam.ui.viewmodel.aset.HomeAsetViewModel
import com.example.final_pam.ui.viewmodel.aset.InsertAsetViewModel

object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer { HomeAsetViewModel(aplikasiKeuangan().container.asetRepository) }
        initializer { InsertAsetViewModel(aplikasiKeuangan().container.asetRepository) }
        initializer { DetailAsetViewModel(createSavedStateHandle(),aplikasiKeuangan().container.asetRepository) }
        initializer { UpdateAsetViewModel(createSavedStateHandle(),aplikasiKeuangan().container.asetRepository) }
    }
    fun CreationExtras.aplikasiKeuangan(): KeuanganApplication =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as KeuanganApplication)
}