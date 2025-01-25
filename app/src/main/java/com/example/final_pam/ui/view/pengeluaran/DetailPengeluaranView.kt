package com.example.final_pam.ui.view.pengeluaran

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
import com.example.final_pam.model.Pengeluaran
import com.example.final_pam.ui.customwidget.CostumeTopAppBar
import com.example.final_pam.ui.navigation.DestinasiNavigasi
import com.example.final_pam.ui.viewmodel.PenyediaViewModel
import com.example.final_pam.ui.viewmodel.pengeluaran.PengeluaranDetailUiState
import com.example.final_pam.ui.viewmodel.pengeluaran.PengeluaranDetailViewModel

object DestinasiDetailPengeluaran : DestinasiNavigasi {
    override val route = "detailPengeluaran"
    override val titleRes = "Detail Pengeluaran"
    const val ID_PENGELUARAN = "id_pengeluaran"
    val routesWithArg = "$route/{$ID_PENGELUARAN}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPengeluaranView(
    navigateBack: () -> Unit,
    navigateToItemUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PengeluaranDetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = "Detail Pengeluaran",
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getPengeluaranById()
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
                    contentDescription = "Edit Pengeluaran"
                )
            }
        }
    ) { innerPadding ->
        PengeluaranDetailStatus(
            modifier = Modifier.padding(innerPadding),
            detailUiState = viewModel.pengeluaranDetailState,
            retryAction = { viewModel.getPengeluaranById() },
            onDeleteClick = {
                viewModel.deletePengeluaran(viewModel.pengeluaranDetailState.let { state ->
                    if (state is PengeluaranDetailUiState.Success) state.pengeluaran.Id_Pengeluaran else ""
                })
                navigateBack()
            }
        )
    }
}

@Composable
fun PengeluaranDetailStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailUiState: PengeluaranDetailUiState,
    onDeleteClick: () -> Unit
) {
    when (detailUiState) {
        is PengeluaranDetailUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is PengeluaranDetailUiState.Success -> {
            if (detailUiState.pengeluaran.Id_Pengeluaran.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Data tidak ditemukan")
                }
            } else {
                PengeluaranDetailContent(
                    pengeluaran = detailUiState.pengeluaran,
                    modifier = modifier.fillMaxWidth(),
                    onDeleteClick = onDeleteClick
                )
            }
        }
        is PengeluaranDetailUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun PengeluaranDetailContent(
    modifier: Modifier = Modifier,
    pengeluaran: Pengeluaran,
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
            PengeluaranDetailComponent(label = "ID Pengeluaran", value = pengeluaran.Id_Pengeluaran)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            PengeluaranDetailComponent(label = "ID Aset", value = pengeluaran.Id_Aset)
            PengeluaranDetailComponent(label = "ID Kategori", value = pengeluaran.Id_Kategori)
            PengeluaranDetailComponent(label = "Tanggal Transaksi", value = pengeluaran.tglTransaksi)
            PengeluaranDetailComponent(label = "Total", value = pengeluaran.total.toString())
            PengeluaranDetailComponent(label = "Catatan", value = pengeluaran.catatan)

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
fun PengeluaranDetailComponent(
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
        text = { Text("Apakah Anda yakin ingin menghapus data?") },
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
