package com.example.final_pam.ui.view.aset

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.final_pam.ui.customwidget.CostumeTopAppBar
import com.example.final_pam.ui.navigation.DestinasiNavigasi
import com.example.final_pam.ui.viewmodel.PenyediaViewModel
import com.example.final_pam.ui.viewmodel.UpdateAsetViewModel
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


object DestinasiUpdateAset : DestinasiNavigasi {
    override val route = "update_aset"
    override val titleRes = "Edit Asset"
    const val ID_ASET = "Id_Aset"
    val routesWithArg = "$route/{$ID_ASET}"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateAsetView(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    viewModel: UpdateAsetViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    var showError by remember { mutableStateOf(false) } // Untuk validasi error
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateAset.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ) { padding ->
        EntryBodyAset(
            modifier = Modifier.padding(padding),
            insertUiState = viewModel.updateUiState,
            onAsetValueChange = viewModel::updateInsertAsetState,
            onSaveClick = {
                val event = viewModel.updateUiState.insertUiEvent
                // Validasi input
                if (event.idAset.isBlank() || event.namaAset.isBlank()) {
                    showError = true
                } else {
                    coroutineScope.launch {
                        viewModel.updateAset()
                        delay(600)
                        withContext(Dispatchers.Main) {
                            onNavigate()
                        }
                    }
                }
            },
            showError = showError // Pass error state
        )
    }
}


