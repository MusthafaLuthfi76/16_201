package com.example.final_pam.ui.view.kategori

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
import com.example.final_pam.model.Kategori
import com.example.final_pam.ui.customwidget.CostumeTopAppBar
import com.example.final_pam.ui.navigation.DestinasiNavigasi
import com.example.final_pam.ui.viewmodel.PenyediaViewModel
import com.example.final_pam.ui.viewmodel.kategori.KategoriDetailUiState
import com.example.final_pam.ui.viewmodel.kategori.KategoriDetailViewModel

object DestinasiDetailKategori : DestinasiNavigasi {
    override val route = "detailKategori"
    override val titleRes = "Detail Kategori"
    const val ID_KATEGORI = "idKategori"
    val routesWithArg = "$route/{$ID_KATEGORI}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailKategoriView(
    navigateBack: () -> Unit,
    navigateToItemUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: KategoriDetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = "Detail Kategori",
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getKategoriById()
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
                    contentDescription = "Edit Kategori"
                )
            }
        }
    ) { innerPadding ->
        KategoriDetailStatus(
            modifier = Modifier.padding(innerPadding),
            detailUiState = viewModel.kategoriDetailState,
            retryAction = { viewModel.getKategoriById() },
            onDeleteClick = {
                viewModel.deleteKategori(viewModel.kategoriDetailState.let { state ->
                    if (state is KategoriDetailUiState.Success) state.kategori.idKategori else ""
                })
                navigateBack()
            }
        )
    }
}

@Composable
fun KategoriDetailStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailUiState: KategoriDetailUiState,
    onDeleteClick: () -> Unit
) {
    when (detailUiState) {
        is KategoriDetailUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is KategoriDetailUiState.Success -> {
            if (detailUiState.kategori.idKategori.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Data tidak ditemukan")
                }
            } else {
                KategoriDetailContent(
                    kategori = detailUiState.kategori,
                    modifier = modifier.fillMaxWidth(),
                    onDeleteClick = onDeleteClick
                )
            }
        }
        is KategoriDetailUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun KategoriDetailContent(
    modifier: Modifier = Modifier,
    kategori: Kategori,
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
            KategoriDetailComponent(label = "ID Kategori", value = kategori.idKategori)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            KategoriDetailComponent(label = "Nama Kategori", value = kategori.namaKategori)

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
fun KategoriDetailComponent(
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
