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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.final_pam.ui.navigation.DestinasiNavigasi
import kotlinx.coroutines.launch

object DestinasiEntryAset: DestinasiNavigasi {
    override val route = "item_entry"
    override val titleRes = "Insert Asset"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryAssetScreen(
    navigateBack: ()-> Unit,
    modifier: Modifier=Modifier,
    viewModel: InsertAsetViewModel= viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier=modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryAset.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ){ innerPadding ->
        EntryBody(
            insertUiState = viewModel.uiState,
            onAssetValueChange = viewModel::updateInsertAsetState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertAset()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBody(
    insertUiState: InsertAsetUiState,
    onAssetValueChange: (InsertAsetUiEvent)->Unit,
    onSaveClick: ()->Unit,
    modifier: Modifier = Modifier
) {
    Column (
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onAssetValueChange,
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
fun FormInput(
    insertUiEvent: InsertAsetUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertAsetUiEvent)->Unit={},
    enabled: Boolean = true
) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.idAset.toString(),  // Convert Int to String for display
            onValueChange = { newValue ->
                val intValue = newValue.toIntOrNull() ?: 0  // Convert input String to Int
                onValueChange(insertUiEvent.copy(idAset = intValue))
            },
            label = { Text("Kode Asset") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.namaAset,
            onValueChange = { onValueChange(insertUiEvent.copy(namaAset = it)) },
            label = { Text("Nama Asset") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.nilaiAset.toString(),  // Convert Double to String for display
            onValueChange = { newValue ->
                val doubleValue = newValue.toDoubleOrNull() ?: 0.0  // Convert input String to Double
                onValueChange(insertUiEvent.copy(nilaiAset = doubleValue))
            },
            label = { Text("Nilai Aset") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
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

