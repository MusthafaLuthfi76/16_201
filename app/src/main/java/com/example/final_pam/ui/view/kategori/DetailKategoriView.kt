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
import com.example.final_pam.ui.view.aset.OnError
import com.example.final_pam.ui.view.aset.OnLoading
import com.example.final_pam.ui.viewmodel.PenyediaViewModel
import com.example.final_pam.ui.viewmodel.kategori.DetailKategoriUiState
import com.example.final_pam.ui.viewmodel.kategori.DetailKategoriViewModel

object DestinasiDetailKategori : DestinasiNavigasi {
    override val route = "detailKategori"
    override val titleRes = "Detail Kategori"
    const val ID_KATEGORI = "Id_Kategori"
    val routesWithArg = "$route/{$ID_KATEGORI}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailKategoriView(
    navigateBack: () -> Unit,
    navigateToItemUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailKategoriViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailKategori.titleRes,
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
        DetailKategoriStatus(
            modifier = Modifier.padding(innerPadding),
            detailKategoriUiState = viewModel.kategoriDetailState,
            retryAction = { viewModel.getKategoriById() },
            onDeleteClick = {
                viewModel.deleteKategori(viewModel.kategoriDetailState.let { state ->
                    if (state is DetailKategoriUiState.Success) state.kategori.idKategori else 0
                })
                navigateBack()
            }
        )
    }
}

@Composable
fun DetailKategoriStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailKategoriUiState: DetailKategoriUiState,
    onDeleteClick: () -> Unit
) {
    when (detailKategoriUiState) {
        is DetailKategoriUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is DetailKategoriUiState.Success -> {
            if (detailKategoriUiState.kategori.idKategori == 0) {
                Box(
                    modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) { Text("Data tidak ditemukan") }
            } else {
                ItemDetailKategori(
                    kategori = detailKategoriUiState.kategori,
                    modifier = modifier.fillMaxWidth(),
                    onDeleteClick = onDeleteClick
                )
            }
        }
        is DetailKategoriUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ItemDetailKategori(
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
            ComponentDetailKategori(judul = "ID Kategori", isinya = kategori.idKategori.toString())
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailKategori(judul = "Nama Kategori", isinya = kategori.namaKategori)

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
fun ComponentDetailKategori(
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
