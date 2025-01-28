package com.example.final_pam.ui.view.pengeluaran

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.final_pam.data.Aset
import com.example.final_pam.data.KategoriDD
import com.example.final_pam.ui.customwidget.CostumeTopAppBar
import com.example.final_pam.ui.navigation.DestinasiNavigasi
import com.example.final_pam.ui.viewmodel.PenyediaViewModel
import com.example.final_pam.ui.viewmodel.pengeluaran.UpdatePengeluaranViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiUpdatePengeluaran : DestinasiNavigasi {
    override val route = "update_pengeluaran"
    override val titleRes = "Edit Pengeluaran"
    const val ID_PENGELUARAN = "id_pengeluaran"
    val routesWithArg = "$route/{$ID_PENGELUARAN}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePengeluaranView(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    viewModel: UpdatePengeluaranViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    // Akses aplikasi keuangan untuk memuat data aset dan kategori
    val context = androidx.compose.ui.platform.LocalContext.current
    val aplikasiKeuangan = context.applicationContext as com.example.final_pam.application.KeuanganApplication
    val asetRepository = aplikasiKeuangan.container.asetRepository
    val kategoriRepository = aplikasiKeuangan.container.kategoriRepository

    // Memuat data aset dan kategori
    LaunchedEffect(Unit) {
        Aset.loadData(asetRepository)
        KategoriDD.loadData(kategoriRepository)
    }

    // Observasi data aset dan kategori
    val options by Aset.options.collectAsState(initial = emptyList())
    val kategoriOptions by KategoriDD.options.collectAsState(initial = emptyList())

    var selectedAset by remember { mutableStateOf(viewModel.updateUiState.insertUiEvent.idAset) }
    var selectedKategori by remember { mutableStateOf(viewModel.updateUiState.insertUiEvent.idKategori) }
    var showError by remember { mutableStateOf(false) } // Tambahkan validasi

    LaunchedEffect(viewModel.updateUiState.insertUiEvent.idAset, viewModel.updateUiState.insertUiEvent.idKategori) {
        selectedAset = viewModel.updateUiState.insertUiEvent.idAset
        selectedKategori = viewModel.updateUiState.insertUiEvent.idKategori
    }

    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdatePengeluaran.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack
            )
        }
    ) { padding ->
        EntryBodyPengeluaran(
            modifier = Modifier.padding(padding),
            insertUiState = viewModel.updateUiState,
            onPengeluaranValueChange = { updatedEvent ->
                viewModel.updateInsertPengeluaranState(updatedEvent)
                selectedAset = updatedEvent.idAset
                selectedKategori = updatedEvent.idKategori
            },
            onSaveClick = {
                val event = viewModel.updateUiState.insertUiEvent
                if (event.idPengeluaran.isBlank() ||
                    selectedAset.isBlank() ||
                    selectedKategori.isBlank() ||
                    event.tglTransaksi.isBlank() ||
                    event.total <= 0 ||
                    event.catatan.isBlank()
                ) {
                    showError = true // Validasi gagal
                } else {
                    coroutineScope.launch {
                        viewModel.updatePengeluaran()
                        delay(600)
                        withContext(Dispatchers.Main) {
                            onNavigate()
                        }
                    }
                }
            },
            options = options,
            selectedAset = selectedAset,
            onSelectedAsetChange = {
                selectedAset = it
                viewModel.updateInsertPengeluaranState(viewModel.updateUiState.insertUiEvent.copy(idAset = it))
            },
            kategoriOptions = kategoriOptions,
            selectedKategori = selectedKategori,
            onSelectedKategoriChange = {
                selectedKategori = it
                viewModel.updateInsertPengeluaranState(viewModel.updateUiState.insertUiEvent.copy(idKategori = it))
            },
            showError = showError // Pass error state
        )
    }
}
