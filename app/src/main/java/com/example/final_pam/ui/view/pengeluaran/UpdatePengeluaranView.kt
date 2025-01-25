package com.example.final_pam.ui.view.pengeluaran

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.final_pam.data.Aset
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
    // Akses aplikasi keuangan untuk memuat data aset
    val context = androidx.compose.ui.platform.LocalContext.current
    val aplikasiKeuangan = context.applicationContext as com.example.final_pam.application.KeuanganApplication
    val asetRepository = aplikasiKeuangan.container.asetRepository

    // Memuat data aset dari repository
    LaunchedEffect(Unit) {
        Aset.loadData(asetRepository)
    }

    // Observasi data aset
    val options by Aset.options.collectAsState(initial = emptyList())
    var selectedAset by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdatePengeluaran.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ) { padding ->
        EntryBodyPengeluaran(
            modifier = Modifier.padding(padding),
            insertUiState = viewModel.updateUiState,
            onPengeluaranValueChange = viewModel::updateInsertPengeluaranState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updatePengeluaran()
                    delay(600) // Simulasi loading jika diperlukan
                    withContext(Dispatchers.Main) {
                        onNavigate()
                    }
                }
            },
            options = options, // Data aset untuk dropdown
            selectedAset = selectedAset, // Aset yang dipilih
            onSelectedAsetChange = { selectedAset = it } // Callback ketika aset dipilih
        )
    }
}

