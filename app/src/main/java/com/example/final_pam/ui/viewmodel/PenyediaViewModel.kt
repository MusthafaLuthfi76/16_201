package com.example.final_pam.ui.viewmodel


import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.final_pam.application.KeuanganApplication
import com.example.final_pam.ui.viewmodel.aset.AsetDetailViewModel
import com.example.final_pam.ui.viewmodel.aset.AsetHomeViewModel
import com.example.final_pam.ui.viewmodel.aset.InsertAsetViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        // HomeAsetViewModel does not need SavedStateHandle
        initializer { AsetHomeViewModel(aplikasiKeuangan().container.asetRepository) }

        // InsertAsetViewModel does not need SavedStateHandle
        initializer { InsertAsetViewModel(aplikasiKeuangan().container.asetRepository) }

        // DetailAsetViewModel needs SavedStateHandle
        initializer { AsetDetailViewModel(createSavedStateHandle(), aplikasiKeuangan().container.asetRepository) }

        // UpdateAsetViewModel needs SavedStateHandle
        initializer { UpdateAsetViewModel(createSavedStateHandle(), aplikasiKeuangan().container.asetRepository) }
    }

    fun CreationExtras.aplikasiKeuangan(): KeuanganApplication =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KeuanganApplication)
}
