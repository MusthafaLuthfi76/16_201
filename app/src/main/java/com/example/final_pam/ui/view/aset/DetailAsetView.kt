package com.example.final_pam.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.final_pam.model.Aset
import com.example.final_pam.ui.customwidget.CostumeTopAppBar
import com.example.final_pam.ui.view.aset.OnError
import com.example.final_pam.ui.view.aset.OnLoading
import com.example.final_pam.ui.viewmodel.PenyediaViewModel
import com.example.final_pam.ui.viewmodel.aset.DetailAsetUiState
import com.example.final_pam.ui.viewmodel.aset.DetailAsetViewModel

object DestinasiDetailAset : DestinasiNavigasi {
    override val route = "detailAset"
    override val titleRes = "Detail Aset"
    const val idAset = "Id_Aset"
    val routesWithArg = "$route/{$idAset}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailAsetView(
    navigateBack: () -> Unit,
    navigateToItemUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailAsetViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailAset.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getAsetById()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemUpdate,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Aset"
                )
            }
        }
    ) { innerPadding ->
        DetailAsetStatus(
            modifier = Modifier.padding(innerPadding),
            detailAsetUiState = viewModel.asetDetailState,
            retryAction = { viewModel.getAsetById() },
            onDeleteClick = {
                viewModel.deleteAset(viewModel.asetDetailState.let { state ->
                    if (state is DetailAsetUiState.Success) state.aset.idAset else 0
                })
                navigateBack()
            }
        )
    }
}

@Composable
fun DetailAsetStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailAsetUiState: DetailAsetUiState,
    onDeleteClick: () -> Unit
) {
    when (detailAsetUiState) {
        is DetailAsetUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is DetailAsetUiState.Success -> {
            if (detailAsetUiState.aset.idAset == 0) {
                Box(
                    modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) { Text("Data tidak ditemukan") }
            } else {
                ItemDetailAset(
                    aset = detailAsetUiState.aset,
                    modifier = modifier.fillMaxWidth(),
                    onDeleteClick = onDeleteClick
                )
            }
        }
        is DetailAsetUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ItemDetailAset(
    modifier: Modifier = Modifier,
    aset: Aset,
    onDeleteClick: () -> Unit
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailAset(judul = "ID Aset", isinya = aset.idAset.toString())
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailAset(judul = "Nama Aset", isinya = aset.namaAset)

            Spacer(modifier = Modifier.padding(8.dp))

            Button(
                onClick = {
                    deleteConfirmationRequired = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Delete")
            }

            if (deleteConfirmationRequired) {
                DeleteConfirmationDialog(
                    onDeleteConfirm = {
                        deleteConfirmationRequired = false
                        onDeleteClick()
                    },
                    onDeleteCancel = { deleteConfirmationRequired = false },
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun ComponentDetailAset(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = judul,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = isinya,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit, onDeleteCancel: () -> Unit, modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text("Delete Data") },
        text = { Text("Apakah anda yakin ingin menghapus data?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Yes")
            }
        }
    )
}
