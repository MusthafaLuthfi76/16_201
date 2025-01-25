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
import com.example.final_pam.ui.navigation.DestinasiNavigasi
import com.example.final_pam.ui.view.aset.DestinasiUpdateAset.ID_ASET
import com.example.final_pam.ui.view.aset.OnError
import com.example.final_pam.ui.view.aset.OnLoading
import com.example.final_pam.ui.viewmodel.PenyediaViewModel
import com.example.final_pam.ui.viewmodel.aset.AsetDetailUiState
import com.example.final_pam.ui.viewmodel.aset.AsetDetailViewModel

object DestinasiDetailAset : DestinasiNavigasi {
    override val route = "detailAset"
    override val titleRes = "Detail Aset"
    const val ID_ASET = "Id_Aset"
    val routesWithArg = "$route/{$ID_ASET}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailAsetView(
    navigateBack: () -> Unit,
    navigateToItemUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AsetDetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = "Detail Aset",
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
        AsetDetailStatus(
            modifier = Modifier.padding(innerPadding),
            detailUiState = viewModel.asetDetailState,
            retryAction = { viewModel.getAsetById() },
            onDeleteClick = {
                viewModel.deleteAset(viewModel.asetDetailState.let { state ->
                    if (state is AsetDetailUiState.Success) state.aset.Id_Aset else ""
                })
                navigateBack()
            }
        )
    }
}

@Composable
fun AsetDetailStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailUiState: AsetDetailUiState,
    onDeleteClick: () -> Unit
) {
    when (detailUiState) {
        is AsetDetailUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is AsetDetailUiState.Success -> {
            if (detailUiState.aset.Id_Aset.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) { Text("Data tidak ditemukan") }
            } else {
                AsetDetailContent(
                    aset = detailUiState.aset,
                    modifier = modifier.fillMaxWidth(),
                    onDeleteClick = onDeleteClick
                )
            }
        }
        is AsetDetailUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun AsetDetailContent(
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
            AsetDetailComponent(label = "ID Aset", value = aset.Id_Aset)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            AsetDetailComponent(label = "Nama Aset", value = aset.Nama_aset)

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
fun AsetDetailComponent(
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

