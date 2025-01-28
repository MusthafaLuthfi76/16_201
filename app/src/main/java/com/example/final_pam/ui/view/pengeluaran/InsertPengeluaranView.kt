package com.example.final_pam.ui.view.pengeluaran

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.final_pam.ui.viewmodel.pengeluaran.InsertPengeluaranUiEvent
import com.example.final_pam.ui.viewmodel.pengeluaran.InsertPengeluaranUiState
import com.example.final_pam.ui.viewmodel.pengeluaran.InsertPengeluaranViewModel
import kotlinx.coroutines.launch
import com.example.final_pam.data.Aset
import com.example.final_pam.ui.customwidget.DynamicSelectTextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.final_pam.data.KategoriDD


object DestinasiInsertPengeluaran : DestinasiNavigasi {
    override val route = "insertPengeluaran"
    override val titleRes = "Insert Pengeluaran"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertPengeluaranView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPengeluaranViewModel = viewModel(factory = PenyediaViewModel.Factory)
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
    var showError by remember { mutableStateOf(false) } // Tambahkan validasi error

    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertPengeluaran.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodyPengeluaran(
            insertUiState = viewModel.uiState,
            onPengeluaranValueChange = viewModel::updateInsertPengeluaranState,
            onSaveClick = {
                val event = viewModel.uiState.insertUiEvent
                if (event.idPengeluaran.isBlank() ||
                    selectedAset.isBlank() ||
                    selectedKategori.isBlank() ||
                    event.tglTransaksi.isBlank() ||
                    event.total <= 0 ||
                    event.catatan.isBlank()
                ) {
                    showError = true // Tampilkan error jika input tidak valid
                } else {
                    coroutineScope.launch {
                        viewModel.insertPengeluaran()
                        navigateBack()
                    }
                }
            },
            options = options,
            selectedAset = selectedAset,
            onSelectedAsetChange = { selectedAset = it },
            kategoriOptions = kategoriOptions,
            selectedKategori = selectedKategori,
            onSelectedKategoriChange = { selectedKategori = it },
            showError = showError, // Pass state error
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}


@Composable
fun EntryBodyPengeluaran(
    insertUiState: InsertPengeluaranUiState,
    onPengeluaranValueChange: (InsertPengeluaranUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    options: List<String>,
    selectedAset: String,
    onSelectedAsetChange: (String) -> Unit,
    kategoriOptions: List<String>,
    selectedKategori: String,
    onSelectedKategoriChange: (String) -> Unit,
    showError: Boolean, // Tambahkan validasi
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputPengeluaran(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onPengeluaranValueChange,
            options = options,
            selectedAset = selectedAset,
            onSelectedAsetChange = onSelectedAsetChange,
            kategoriOptions = kategoriOptions,
            selectedKategori = selectedKategori,
            onSelectedKategoriChange = onSelectedKategoriChange,
            showError = showError, // Pass error state ke form
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
fun FormInputPengeluaran(
    insertUiEvent: InsertPengeluaranUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertPengeluaranUiEvent) -> Unit = {},
    options: List<String>,
    selectedAset: String,
    onSelectedAsetChange: (String) -> Unit,
    kategoriOptions: List<String>,
    selectedKategori: String,
    onSelectedKategoriChange: (String) -> Unit,
    showError: Boolean, // Parameter untuk menampilkan error
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Field ID Pengeluaran
        OutlinedTextField(
            value = insertUiEvent.idPengeluaran,
            onValueChange = { onValueChange(insertUiEvent.copy(idPengeluaran = it)) },
            label = { Text("ID Pengeluaran") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = showError && insertUiEvent.idPengeluaran.isBlank()
        )
        if (showError && insertUiEvent.idPengeluaran.isBlank()) {
            Text("ID Pengeluaran tidak boleh kosong", color = MaterialTheme.colorScheme.error)
        }

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
            modifier = Modifier.fillMaxWidth(),
            isError = showError && selectedAset.isBlank()
        )
        if (showError && selectedAset.isBlank()) {
            Text("ID Aset tidak boleh kosong", color = MaterialTheme.colorScheme.error)
        }

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
            modifier = Modifier.fillMaxWidth(),
            isError = showError && selectedKategori.isBlank()
        )
        if (showError && selectedKategori.isBlank()) {
            Text("ID Kategori tidak boleh kosong", color = MaterialTheme.colorScheme.error)
        }

        // Field Tanggal Transaksi
        OutlinedTextField(
            value = insertUiEvent.tglTransaksi,
            onValueChange = { onValueChange(insertUiEvent.copy(tglTransaksi = it)) },
            label = { Text("Tanggal Transaksi") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = showError && insertUiEvent.tglTransaksi.isBlank()
        )
        if (showError && insertUiEvent.tglTransaksi.isBlank()) {
            Text("Tanggal Transaksi tidak boleh kosong", color = MaterialTheme.colorScheme.error)
        }

        // Field Total
        OutlinedTextField(
            value = insertUiEvent.total.toString(),
            onValueChange = { onValueChange(insertUiEvent.copy(total = it.toIntOrNull() ?: 0)) },
            label = { Text("Total") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = showError && insertUiEvent.total <= 0
        )
        if (showError && insertUiEvent.total <= 0) {
            Text("Total harus lebih besar dari 0", color = MaterialTheme.colorScheme.error)
        }

        // Field Catatan
        OutlinedTextField(
            value = insertUiEvent.catatan,
            onValueChange = { onValueChange(insertUiEvent.copy(catatan = it)) },
            label = { Text("Catatan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = showError && insertUiEvent.catatan.isBlank()
        )
        if (showError && insertUiEvent.catatan.isBlank()) {
            Text("Catatan tidak boleh kosong", color = MaterialTheme.colorScheme.error)
        }
    }
}


