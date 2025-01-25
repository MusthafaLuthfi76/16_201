package com.example.final_pam.ui.view.aset

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.final_pam.R
import com.example.final_pam.model.Aset
import com.example.final_pam.ui.navigation.DestinasiNavigasi
import com.example.final_pam.ui.viewmodel.aset.HomeAsetUiState
import com.example.final_pam.ui.viewmodel.aset.HomeAsetViewModel

object DestinasiHomeAset : DestinasiNavigasi{
    override val route = "homeAset"
    override val titleRes = "Home Aset"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAsetView(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    viewModel: HomeAsetViewModel = viewModel()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier,
        topBar = {
            androidx.compose.material3.TopAppBar(
                title = { Text("Aset") },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Aset")
            }
        }
    ) { innerPadding ->
        AsetStatus(
            asetUiState = viewModel.asetUIState,
            retryAction = { viewModel.getAset() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteAset(it.idAset)
                viewModel.getAset()
            }
        )
    }
}

@Composable
fun AsetStatus(
    asetUiState: HomeAsetUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Aset) -> Unit = {},
    onDetailClick: (Int) -> Unit
) {
    when (asetUiState) {
        is HomeAsetUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeAsetUiState.Success -> {
            if (asetUiState.aset.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Aset")
                }
            } else {
                AsetLayout(
                    aset = asetUiState.aset,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.idAset)
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }
        }
        is HomeAsetUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun AsetLayout(
    aset: List<Aset>,
    modifier: Modifier = Modifier,
    onDetailClick: (Aset) -> Unit,
    onDeleteClick: (Aset) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(aset) { aset ->
            AsetCard(
                aset = aset,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(aset) },
                onDeleteClick = {
                    onDeleteClick(aset)
                }
            )
        }
    }
}

@Composable
fun AsetCard(
    aset: Aset,
    modifier: Modifier = Modifier,
    onDeleteClick: (Aset) -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(8.dp, shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE0F7FA))
                .padding(16.dp)
        ) {
            // Placeholder for Aset image
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Color(0xFF0097A7), shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.asset), // Replace with your placeholder
                    contentDescription = "Aset Image",
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Aset Information
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = aset.namaAset,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color(0xFF00796B)
                )
                Text(
                    text = "ID Aset: ${aset.idAset}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF004D40)
                )
            }

            // Delete button
            IconButton(onClick = { onDeleteClick(aset) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Aset",
                    tint = Color.Red
                )
            }
        }
    }
}

// Composable to show loading status
@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Loading...")
    }
}

// Composable to show error status
@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Failed to load data.")
        Button(onClick = retryAction) {
            Text("Retry")
        }
    }
}
