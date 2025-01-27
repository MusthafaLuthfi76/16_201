package com.example.final_pam.ui.view.pendapatan

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
import com.example.final_pam.ui.viewmodel.pendapatan.UpdatePendapatanViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiUpdatePendapatan : DestinasiNavigasi {
    override val route = "update_pendapatan"
    override val titleRes = "Edit Pendapatan"
    const val ID_PENDAPATAN = "id_pendapatan"
    val routesWithArg = "$route/{$ID_PENDAPATAN}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePendapatanView(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    viewModel: UpdatePendapatanViewModel = viewModel(factory = PenyediaViewModel.Factory)
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
    var selectedAset by remember { mutableStateOf("") }
    var selectedKategori by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdatePendapatan.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ) { padding ->
        EntryBodyPendapatan(
            modifier = Modifier.padding(padding),
            insertUiState = viewModel.updateUiState,
            onPendapatanValueChange = viewModel::updateInsertPendapatanState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updatePendapatan()
                    delay(600) // Simulasi loading jika diperlukan
                    withContext(Dispatchers.Main) {
                        onNavigate()
                    }
                }
            },
            options = options, // Data aset untuk dropdown
            selectedAset = selectedAset, // Aset yang dipilih
            onSelectedAsetChange = { selectedAset = it }, // Callback ketika aset dipilih
            kategoriOptions = kategoriOptions, // Data kategori untuk dropdown
            selectedKategori = selectedKategori, // Kategori yang dipilih
            onSelectedKategoriChange = { selectedKategori = it } // Callback ketika kategori dipilih
        )
    }
}

