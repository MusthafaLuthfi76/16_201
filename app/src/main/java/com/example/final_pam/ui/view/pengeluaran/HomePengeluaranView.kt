package com.example.final_pam.ui.view.pengeluaran

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.final_pam.R
import com.example.final_pam.model.Pengeluaran
import com.example.final_pam.ui.customwidget.CostumeTopAppBar
import com.example.final_pam.ui.navigation.DestinasiNavigasi
import com.example.final_pam.ui.viewmodel.HomeViewModel
import com.example.final_pam.ui.viewmodel.PenyediaViewModel
import com.example.final_pam.ui.viewmodel.pengeluaran.PengeluaranHomeUiState
import com.example.final_pam.ui.viewmodel.pengeluaran.PengeluaranHomeViewModel
import kotlinx.coroutines.launch

object DestinasiHomePengeluaran : DestinasiNavigasi {
    override val route = "homePengeluaran"
    override val titleRes = "Home Pengeluaran"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePengeluaranView(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: PengeluaranHomeViewModel = viewModel(factory = PenyediaViewModel.Factory),
    homeViewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory),
    navigateBack: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val saldo by homeViewModel.saldo // Mengambil saldo dari HomeViewModel
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = "Daftar Pengeluaran",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getPengeluaran()
                    homeViewModel.refreshData() // Refresh saldo
                },
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (saldo < 0) {
                        showDialog = true
                    } else {
                        navigateToItemEntry()
                    }
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Pengeluaran")
            }
        }
    ) { innerPadding ->
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Saldo Minus") },
                text = { Text("Keuangan Anda sudah minus, apakah Anda yakin ingin menambah data pengeluaran?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showDialog = false
                            navigateToItemEntry()
                        }
                    ) {
                        Text("Iya")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Batal")
                    }
                }
            )
        }

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // Status Data Pengeluaran
            PengeluaranHomeStatus(
                homeUiState = viewModel.pengeluaranUIState,
                retryAction = { viewModel.getPengeluaran() },
                modifier = Modifier.fillMaxSize(),
                onDetailClick = onDetailClick,
                onDeleteClick = {
                    viewModel.deletePengeluaran(it.Id_Pengeluaran)
                    viewModel.getPengeluaran()
                }
            )
        }
    }
}

@Composable
fun PengeluaranHomeStatus(
    homeUiState: PengeluaranHomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Pengeluaran) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (homeUiState) {
        is PengeluaranHomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is PengeluaranHomeUiState.Success -> {
            if (homeUiState.pengeluaran.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data pengeluaran")
                }
            } else {
                PengeluaranLayout(
                    pengeluaran = homeUiState.pengeluaran,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.Id_Pengeluaran)
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }
        }
        is PengeluaranHomeUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}


// Menampilkan loading message
@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loadingg),
        contentDescription = stringResource(R.string.loading)
    )
}

// Menampilkan error message
@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.error), contentDescription = null
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun PengeluaranLayout(
    pengeluaran: List<Pengeluaran>,
    modifier: Modifier = Modifier,
    onDetailClick: (Pengeluaran) -> Unit,
    onDeleteClick: (Pengeluaran) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(pengeluaran) { pengeluaranItem ->
            PengeluaranCard(
                pengeluaran = pengeluaranItem,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(pengeluaranItem) },
                onDeleteClick = {
                    onDeleteClick(pengeluaranItem)
                }
            )
        }
    }
}


@Composable
fun PengeluaranCard(
    pengeluaran: Pengeluaran,
    modifier: Modifier = Modifier,
    onDeleteClick: (Pengeluaran) -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(8.dp, shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFCDD2)), // Warna merah
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Informasi pengeluaran
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = " ${pengeluaran.catatan}",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.Black
                )
                Text(
                    text = "Tanggal: ${pengeluaran.tglTransaksi}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    text = "Total: Rp ${pengeluaran.total}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            // Tombol delete
            IconButton(onClick = { onDeleteClick(pengeluaran) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Red
                )
            }
        }
    }
}
