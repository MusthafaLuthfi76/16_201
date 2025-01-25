package com.example.final_pam.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.final_pam.ui.view.DestinasiDetailAset
import com.example.final_pam.ui.view.DetailAsetView
import com.example.final_pam.ui.view.aset.DestinasiEntryAset
import com.example.final_pam.ui.view.aset.DestinasiHomeAset
import com.example.final_pam.ui.view.aset.DestinasiUpdateAset
import com.example.final_pam.ui.view.aset.EntryAssetScreen
import com.example.final_pam.ui.view.aset.HomeAsetView
import com.example.final_pam.ui.view.aset.UpdateAsetView

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeAset.route,
        modifier = Modifier,
    ) {
        composable(DestinasiHomeAset.route) {
            HomeAsetView(
                navigateToItemEntry = { navController.navigate(DestinasiEntryAset.route) },
                onDetailClick = { idAset ->
                    navController.navigate("${DestinasiDetailAset.route}/$idAset")
                }
            )
        }
        composable(DestinasiEntryAset.route) {
            EntryAssetScreen(navigateBack = {
                navController.navigate(DestinasiHomeAset.route) {
                    popUpTo(DestinasiHomeAset.route) {
                        inclusive = true
                    }
                }
            })
        }
        composable(
            DestinasiDetailAset.routesWithArg,
            arguments = listOf(navArgument(DestinasiDetailAset.idAset) {
                type = NavType.StringType
            })
        ) {
            val idAset = it.arguments?.getString(DestinasiDetailAset.idAset)
            idAset?.let { idAset ->
                DetailAsetView(
                    navigateToItemUpdate = { navController.navigate("${DestinasiUpdateAset.route}/$idAset") },
                    navigateBack = {
                        navController.navigate(DestinasiHomeAset.route) {
                            popUpTo(DestinasiHomeAset.route) { inclusive = true }
                        }
                    }
                )
            }
        }
        composable(
            DestinasiUpdateAset.routesWithArg,
            arguments = listOf(navArgument(DestinasiUpdateAset.ID_ASET) {
                type = NavType.StringType
            })
        ) {
            val idAset = it.arguments?.getString(DestinasiUpdateAset.ID_ASET)
            idAset?.let { idAset ->
                UpdateAsetView(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }
    }
}
