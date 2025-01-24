package com.example.final_pam.ui.view.kategori

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.final_pam.model.Kategori
import com.example.final_pam.ui.customwidget.CostumeTopAppBar
import com.example.final_pam.ui.navigation.DestinasiNavigasi
import com.example.final_pam.ui.view.aset.OnError
import com.example.final_pam.ui.view.aset.OnLoading
import com.example.final_pam.ui.viewmodel.kategori.HomeKategoriUiState
import com.example.final_pam.ui.viewmodel.kategori.HomeKategoriViewModel

object DestinasiHomeKategori : DestinasiNavigasi {
    override val route = "home_kategori"
    override val titleRes = "Kategori"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeKategoriView(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    viewModel: HomeKategoriViewModel = viewModel()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeKategori.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getKategori()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Kategori")
            }
        }
    ) { innerPadding ->
        HomeKategoriStatus(
            kategoriUiState = viewModel.kategoriUIState,
            retryAction = { viewModel.getKategori() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = { viewModel.deleteKategori(it.idKategori) }
        )
    }
}

@Composable
fun HomeKategoriStatus(
    kategoriUiState: HomeKategoriUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Kategori) -> Unit = {},
    onDetailClick: (Int) -> Unit
) {
    when (kategoriUiState) {
        is HomeKategoriUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomeKategoriUiState.Success ->
            if (kategoriUiState.kategori.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Kategori")
                }
            } else {
                KategoriLayout(
                    kategori = kategoriUiState.kategori,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.idKategori)
                    },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        is HomeKategoriUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun KategoriLayout(
    kategori: List<Kategori>,
    modifier: Modifier = Modifier,
    onDetailClick: (Kategori) -> Unit,
    onDeleteClick: (Kategori) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(kategori) { kategori ->
            KategoriCard(
                kategori = kategori,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(kategori) },
                onDeleteClick = { onDeleteClick(kategori) }
            )
        }
    }
}

@Composable
fun KategoriCard(
    kategori: Kategori,
    modifier: Modifier = Modifier,
    onDeleteClick: (Kategori) -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(8.dp, shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE0F7FA)) // Background color
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.width(16.dp))

            // Display Kategori Information
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = kategori.namaKategori,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFF00796B) // Dark green text
                )
                Text(
                    text = "ID: ${kategori.idKategori}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF004D40)
                )
            }

            // Delete button
            IconButton(onClick = { onDeleteClick(kategori) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Red
                )
            }
        }
    }
}
