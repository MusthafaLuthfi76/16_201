package com.example.final_pam.ui.view.kategori

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
import com.example.final_pam.ui.viewmodel.kategori.UpdateKategoriViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiUpdateKategori : DestinasiNavigasi {
    override val route = "update_kategori"
    override val titleRes = "Edit Kategori"
    const val ID_KATEGORI = "Id_Kategori"
    val routesWithArg = "$route/{$ID_KATEGORI}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateKategoriView(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    viewModel: UpdateKategoriViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateKategori.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ) { padding ->
        EntryBodyKategori(
            modifier = Modifier.padding(padding),
            insertUiState = viewModel.updateKategoriUiState,
            onKategoriValueChange = viewModel::updateInsertKategoriState,
            onSaveClick = {
                coroutineScope.launch {
                    try {
                        viewModel.updateKategori()
                        delay(600) // Simulate delay after update
                        withContext(Dispatchers.Main) {
                            onNavigate() // Navigate back after saving
                        }
                    } catch (e: Exception) {
                        e.printStackTrace() // Log any exception
                    }
                }
            }
        )
    }
}
