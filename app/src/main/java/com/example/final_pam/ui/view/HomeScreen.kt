package com.example.final_pam.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.final_pam.ui.navigation.DestinasiNavigasi
import com.example.final_pam.ui.viewmodel.HomeViewModel
import com.example.final_pam.ui.viewmodel.PenyediaViewModel

object DestinasiHomeScreen : DestinasiNavigasi {
    override val route = "home"
    override val titleRes = "Home"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onPendapatanClick: () -> Unit,
    onPengeluaranClick: () -> Unit,
    navigateToAset: () -> Unit,
    navigateToKategori: () -> Unit,
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val saldo by remember { viewModel.saldo }
    val totalPengeluaran by remember { viewModel.totalPengeluaran }
    val totalPendapatan by remember { viewModel.totalPendapatan }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home") },
                actions = {
                    IconButton(onClick = {
                        // Memanggil fungsi untuk merefresh data
                        viewModel.refreshData()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Refresh, // Icon Refresh
                            contentDescription = "Refresh Data"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Aset") },
                    label = { Text("Aset") },
                    selected = false,
                    onClick = navigateToAset
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Menu, contentDescription = "Kategori") },
                    label = { Text("Kategori") },
                    selected = false,
                    onClick = navigateToKategori
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Saldo Box
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0)),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Saldo Anda",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Rp ${saldo.formatCurrency()}",
                        color = if (saldo >= 0) Color(0xFF00796B) else Color.Red,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Total Pengeluaran
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onPengeluaranClick() },
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Total Pengeluaran",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Rp ${totalPengeluaran.formatCurrency()}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            // Total Pendapatan
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onPendapatanClick() },
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Total Pendapatan",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Rp ${totalPendapatan.formatCurrency()}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

// Extension function to format currency
fun Int.formatCurrency(): String {
    return String.format("%,d", this).replace(',', '.')
}
