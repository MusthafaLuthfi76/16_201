package com.example.final_pam.ui.view.pendapatan

import com.example.final_pam.ui.customwidget.CostumeTopAppBar
import com.example.final_pam.ui.navigation.DestinasiNavigasi
import com.example.final_pam.ui.viewmodel.PenyediaViewModel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.final_pam.data.Aset
import com.example.final_pam.data.KategoriDD
import com.example.final_pam.ui.customwidget.DynamicSelectTextField
import com.example.final_pam.ui.viewmodel.InsertPendapatanUiEvent
import com.example.final_pam.ui.viewmodel.InsertPendapatanUiState
import com.example.final_pam.ui.viewmodel.InsertPendapatanViewModel
import kotlinx.coroutines.launch

object DestinasiInsertPendapatan : DestinasiNavigasi {
    override val route = "insertPendapatan"
    override val titleRes = "Insert Pendapatan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertPendapatanView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPendapatanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    // Akses aplikasi keuangan dari context
    val context = androidx.compose.ui.platform.LocalContext.current
    val aplikasiKeuangan = context.applicationContext as com.example.final_pam.application.KeuanganApplication
    val asetRepository = aplikasiKeuangan.container.asetRepository
    val kategoriRepository = aplikasiKeuangan.container.kategoriRepository

    // Memuat data aset dari repository
    LaunchedEffect(Unit) {
        Aset.loadData(asetRepository)
        KategoriDD.loadData(kategoriRepository)
    }

    // Observasi data aset
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
                title = DestinasiInsertPendapatan.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodyPendapatan(
            insertUiState = viewModel.uiState,
            onPendapatanValueChange = viewModel::updateInsertPendapatanState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPendapatan()
                    navigateBack()
                }
            },
            options = options,
            selectedAset = selectedAset,
            onSelectedAsetChange = { selectedAset = it },
            kategoriOptions = kategoriOptions,
            selectedKategori = selectedKategori,
            onSelectedKategoriChange = { selectedKategori = it },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBodyPendapatan(
    insertUiState: InsertPendapatanUiState,
    onPendapatanValueChange: (InsertPendapatanUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    options: List<String>,
    selectedAset: String,
    onSelectedAsetChange: (String) -> Unit,
    kategoriOptions: List<String>,
    selectedKategori: String,
    onSelectedKategoriChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputPendapatan(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onPendapatanValueChange,
            options = options,
            selectedAset = selectedAset,
            onSelectedAsetChange = onSelectedAsetChange,
            kategoriOptions = kategoriOptions,
            selectedKategori = selectedKategori,
            onSelectedKategoriChange = onSelectedKategoriChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputPendapatan(
    insertUiEvent: InsertPendapatanUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertPendapatanUiEvent) -> Unit = {},
    options: List<String>,
    selectedAset: String,
    onSelectedAsetChange: (String) -> Unit,
    kategoriOptions: List<String>,
    selectedKategori: String,
    onSelectedKategoriChange: (String) -> Unit,
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.idPendapatan,
            onValueChange = { onValueChange(insertUiEvent.copy(idPendapatan = it)) },
            label = { Text("ID Pendapatan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        // Dropdown untuk ID Aset
        DynamicSelectTextField(
            selectedValue = selectedAset,
            options = options,
            label = "ID Aset",
            onValueChangedEvent = { value ->
                val idAset = value.substringBefore(":").trim() // Ambil hanya Id_aset
                onSelectedAsetChange(value)
                onValueChange(insertUiEvent.copy(idAset = idAset))
            },
            modifier = Modifier.fillMaxWidth()
        )
        // Dropdown untuk ID Kategori
        DynamicSelectTextField(
            selectedValue = selectedKategori,
            options = kategoriOptions,
            label = "ID Kategori",
            onValueChangedEvent = { value ->
                val idKategori = value.substringBefore(":").trim() // Ambil hanya Id_kategori
                onSelectedKategoriChange(value)
                onValueChange(insertUiEvent.copy(idKategori = idKategori))
            },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = insertUiEvent.tglTransaksi,
            onValueChange = { onValueChange(insertUiEvent.copy(tglTransaksi = it)) },
            label = { Text("Tanggal Transaksi") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        Text(
            text = "Format Tanggal: DD/MM/YYYY",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.padding(start = 12.dp)
        )
        OutlinedTextField(
            value = insertUiEvent.total.toString(),
            onValueChange = { onValueChange(insertUiEvent.copy(total = it.toIntOrNull() ?: 0)) },
            label = { Text("Total") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.catatan,
            onValueChange = { onValueChange(insertUiEvent.copy(catatan = it)) },
            label = { Text("Catatan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        if (enabled) {
            Text(
                text = "Isi Semua Data",
                modifier = Modifier.padding(12.dp)
            )
        }
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}
