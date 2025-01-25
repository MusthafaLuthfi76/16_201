package com.example.final_pam.ui.view.pendapatan

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
import com.example.final_pam.model.Pendapatan
import com.example.final_pam.ui.customwidget.CostumeTopAppBar
import com.example.final_pam.ui.navigation.DestinasiNavigasi
import com.example.final_pam.ui.viewmodel.PenyediaViewModel
import com.example.final_pam.ui.viewmodel.pendapatan.PendapatanDetailUiState
import com.example.final_pam.ui.viewmodel.pendapatan.PendapatanDetailViewModel

object DestinasiDetailPendapatan : DestinasiNavigasi {
    override val route = "detailPendapatan"
    override val titleRes = "Detail Pendapatan"
    const val ID_PENDAPATAN = "idPendapatan"
    val routesWithArg = "$route/{$ID_PENDAPATAN}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPendapatanView(
    navigateBack: () -> Unit,
    navigateToItemUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PendapatanDetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = "Detail Pendapatan",
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getPendapatanById()
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
                    contentDescription = "Edit Pendapatan"
                )
            }
        }
    ) { innerPadding ->
        PendapatanDetailStatus(
            modifier = Modifier.padding(innerPadding),
            detailUiState = viewModel.pendapatanDetailState,
            retryAction = { viewModel.getPendapatanById() },
            onDeleteClick = {
                viewModel.deletePendapatan(viewModel.pendapatanDetailState.let { state ->
                    if (state is PendapatanDetailUiState.Success) state.pendapatan.Id_Pendapatan else ""
                })
                navigateBack()
            }
        )
    }
}

@Composable
fun PendapatanDetailStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailUiState: PendapatanDetailUiState,
    onDeleteClick: () -> Unit
) {
    when (detailUiState) {
        is PendapatanDetailUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is PendapatanDetailUiState.Success -> {
            if (detailUiState.pendapatan.Id_Pendapatan.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Data tidak ditemukan")
                }
            } else {
                PendapatanDetailContent(
                    pendapatan = detailUiState.pendapatan,
                    modifier = modifier.fillMaxWidth(),
                    onDeleteClick = onDeleteClick
                )
            }
        }
        is PendapatanDetailUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun PendapatanDetailContent(
    modifier: Modifier = Modifier,
    pendapatan: Pendapatan,
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
            PendapatanDetailComponent(label = "ID Pendapatan", value = pendapatan.Id_Pendapatan)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            PendapatanDetailComponent(label = "ID Aset", value = pendapatan.Id_Aset)
            PendapatanDetailComponent(label = "ID Kategori", value = pendapatan.Id_Kategori)
            PendapatanDetailComponent(label = "Tanggal Transaksi", value = pendapatan.tglTransaksi)
            PendapatanDetailComponent(label = "Total", value = pendapatan.total.toString())
            PendapatanDetailComponent(label = "Catatan", value = pendapatan.catatan)

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
fun PendapatanDetailComponent(
    modifier: Modifier = Modifier,
    label: String,
    value: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
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
