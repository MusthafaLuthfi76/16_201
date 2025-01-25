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
import com.example.final_pam.ui.viewmodel.kategori.InsertKategoriViewModel
import com.example.final_pam.ui.viewmodel.kategori.KategoriDetailViewModel
import com.example.final_pam.ui.viewmodel.kategori.KategoriHomeViewModel
import com.example.final_pam.ui.viewmodel.kategori.UpdateKategoriViewModel
import com.example.final_pam.ui.viewmodel.pendapatan.PendapatanDetailViewModel
import com.example.final_pam.ui.viewmodel.pendapatan.PendapatanHomeViewModel
import com.example.final_pam.ui.viewmodel.pendapatan.UpdatePendapatanViewModel
import com.example.final_pam.ui.viewmodel.pengeluaran.InsertPengeluaranViewModel
import com.example.final_pam.ui.viewmodel.pengeluaran.PengeluaranDetailViewModel
import com.example.final_pam.ui.viewmodel.pengeluaran.PengeluaranHomeViewModel
import com.example.final_pam.ui.viewmodel.pengeluaran.UpdatePengeluaranViewModel

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


        // HomeKategoriViewModel does not need SavedStateHandle
        initializer { KategoriHomeViewModel(aplikasiKeuangan().container.kategoriRepository) }
        // InsertKategoriViewModel does not need SavedStateHandle
        initializer { InsertKategoriViewModel(aplikasiKeuangan().container.kategoriRepository) }
        // DetailKategoriViewModel needs SavedStateHandle
        initializer { KategoriDetailViewModel(createSavedStateHandle(), aplikasiKeuangan().container.kategoriRepository) }
        // UpdateKategoriViewModel needs SavedStateHandle
        initializer { UpdateKategoriViewModel(createSavedStateHandle(), aplikasiKeuangan().container.kategoriRepository) }


        // HomePendapatanViewModel does not need SavedStateHandle
        initializer { PendapatanHomeViewModel(aplikasiKeuangan().container.pendapatanRepository) }
        // InsertPendapatanViewModel does not need SavedStateHandle
        initializer { InsertPendapatanViewModel(aplikasiKeuangan().container.pendapatanRepository) }
        // DetailPendapatanViewModel needs SavedStateHandle
        initializer { PendapatanDetailViewModel(createSavedStateHandle(), aplikasiKeuangan().container.pendapatanRepository) }
        // UpdatePendapatanViewModel needs SavedStateHandle
        initializer { UpdatePendapatanViewModel(createSavedStateHandle(), aplikasiKeuangan().container.pendapatanRepository) }

        // HomePengeluaranViewModel does not need SavedStateHandle
        initializer { PengeluaranHomeViewModel(aplikasiKeuangan().container.pengeluaranRepository) }
        // InsertPengeluaranViewModel does not need SavedStateHandle
        initializer { InsertPengeluaranViewModel(aplikasiKeuangan().container.pengeluaranRepository) }
        // DetailPengeluaranViewModel needs SavedStateHandle
        initializer { PengeluaranDetailViewModel(createSavedStateHandle(), aplikasiKeuangan().container.pengeluaranRepository) }
        // UpdatePengeluaranViewModel needs SavedStateHandle
        initializer { UpdatePengeluaranViewModel(createSavedStateHandle(), aplikasiKeuangan().container.pengeluaranRepository) }

        initializer { HomeViewModel(aplikasiKeuangan().container.pendapatanRepository, aplikasiKeuangan().container.pengeluaranRepository) }
    }

    fun CreationExtras.aplikasiKeuangan(): KeuanganApplication =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KeuanganApplication)
}
