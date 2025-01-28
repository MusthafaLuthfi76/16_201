package com.example.final_pam.ui.view.aset

import com.example.final_pam.ui.customwidget.CostumeTopAppBar
import com.example.final_pam.ui.viewmodel.PenyediaViewModel
import com.example.final_pam.ui.viewmodel.aset.InsertAsetUiEvent
import com.example.final_pam.ui.viewmodel.aset.InsertAsetUiState
import com.example.final_pam.ui.viewmodel.aset.InsertAsetViewModel

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
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.final_pam.ui.navigation.DestinasiNavigasi
import kotlinx.coroutines.launch

object DestinasiInsertAset : DestinasiNavigasi {
    override val route = "insertAset"
    override val titleRes = "Insert Aset"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertAsetView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertAsetViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    var showError by remember { mutableStateOf(false) } // Untuk validasi error
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertAset.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodyAset(
            insertUiState = viewModel.uiState,
            onAsetValueChange = viewModel::updateInsertAsetState,
            onSaveClick = {
                val event = viewModel.uiState.insertUiEvent
                // Validasi input
                if (event.idAset.isBlank() || event.namaAset.isBlank()) {
                    showError = true
                } else {
                    coroutineScope.launch {
                        viewModel.insertAset()
                        navigateBack()
                    }
                }
            },
            showError = showError, // Pass error state ke form
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBodyAset(
    insertUiState: InsertAsetUiState,
    onAsetValueChange: (InsertAsetUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    showError: Boolean, // Parameter validasi
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputAset(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onAsetValueChange,
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
fun FormInputAset(
    insertUiEvent: InsertAsetUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertAsetUiEvent) -> Unit = {},
    showError: Boolean, // Parameter untuk validasi error
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.idAset,
            onValueChange = { onValueChange(insertUiEvent.copy(idAset = it)) },
            label = { Text("ID Aset") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = showError && insertUiEvent.idAset.isBlank()
        )
        if (showError && insertUiEvent.idAset.isBlank()) {
            Text("ID Aset tidak boleh kosong", color = MaterialTheme.colorScheme.error)
        }

        OutlinedTextField(
            value = insertUiEvent.namaAset,
            onValueChange = { onValueChange(insertUiEvent.copy(namaAset = it)) },
            label = { Text("Nama Aset") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = showError && insertUiEvent.namaAset.isBlank()
        )
        if (showError && insertUiEvent.namaAset.isBlank()) {
            Text("Nama Aset tidak boleh kosong", color = MaterialTheme.colorScheme.error)
        }
    }
}


