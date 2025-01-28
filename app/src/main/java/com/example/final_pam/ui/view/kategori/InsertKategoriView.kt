package com.example.final_pam.ui.view.kategori

import com.example.final_pam.ui.customwidget.CostumeTopAppBar
import com.example.final_pam.ui.viewmodel.PenyediaViewModel
import com.example.final_pam.ui.viewmodel.kategori.InsertKategoriUiEvent
import com.example.final_pam.ui.viewmodel.kategori.InsertKategoriUiState
import com.example.final_pam.ui.viewmodel.kategori.InsertKategoriViewModel

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.final_pam.ui.navigation.DestinasiNavigasi
import kotlinx.coroutines.launch

object DestinasiInsertKategori : DestinasiNavigasi {
    override val route = "insertKategori"
    override val titleRes = "Insert Kategori"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertKategoriView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertKategoriViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    var showError by remember { mutableStateOf(false) } // Untuk validasi error
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertKategori.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodyKategori(
            insertUiState = viewModel.uiState,
            onKategoriValueChange = viewModel::updateInsertKategoriState,
            onSaveClick = {
                val event = viewModel.uiState.insertUiEvent
                // Validasi input
                if (event.idKategori.isBlank() || event.namaKategori.isBlank()) {
                    showError = true
                } else {
                    coroutineScope.launch {
                        viewModel.insertKategori()
                        navigateBack()
                    }
                }
            },
            showError = showError, // Pass error state
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBodyKategori(
    insertUiState: InsertKategoriUiState,
    onKategoriValueChange: (InsertKategoriUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    showError: Boolean, // Parameter untuk validasi error
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputKategori(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onKategoriValueChange,
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
fun FormInputKategori(
    insertUiEvent: InsertKategoriUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertKategoriUiEvent) -> Unit = {},
    showError: Boolean, // Parameter untuk validasi error
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.idKategori,
            onValueChange = { onValueChange(insertUiEvent.copy(idKategori = it)) },
            label = { Text("ID Kategori") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = showError && insertUiEvent.idKategori.isBlank()
        )
        if (showError && insertUiEvent.idKategori.isBlank()) {
            Text("ID Kategori tidak boleh kosong", color = MaterialTheme.colorScheme.error)
        }

        OutlinedTextField(
            value = insertUiEvent.namaKategori,
            onValueChange = { onValueChange(insertUiEvent.copy(namaKategori = it)) },
            label = { Text("Nama Kategori") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = showError && insertUiEvent.namaKategori.isBlank()
        )
        if (showError && insertUiEvent.namaKategori.isBlank()) {
            Text("Nama Kategori tidak boleh kosong", color = MaterialTheme.colorScheme.error)
        }
    }
}
