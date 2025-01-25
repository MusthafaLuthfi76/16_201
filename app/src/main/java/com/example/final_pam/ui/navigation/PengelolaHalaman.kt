package com.example.final_pam.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.final_pam.ui.view.*
import com.example.final_pam.ui.view.aset.*
import com.example.final_pam.ui.view.kategori.*
import com.example.final_pam.ui.view.pendapatan.*
import com.example.final_pam.ui.view.pengeluaran.*

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeScreen.route,
        modifier = Modifier,
    ) {
        // Halaman HomeScreen
        composable(DestinasiHomeScreen.route) {
            HomeScreen(
                navigateToAset = { navController.navigate(DestinasiHomeAset.route) },
                navigateToKategori = { navController.navigate(DestinasiHomeKategori.route) },
                onPendapatanClick = { navController.navigate(DestinasiHomePendapatan.route) },
                onPengeluaranClick = { navController.navigate(DestinasiHomePengeluaran.route) }
            )
        }

        // Halaman Aset
        composable(DestinasiHomeAset.route) {
            HomeAsetView(
                navigateToItemEntry = { navController.navigate(DestinasiInsertAset.route) },
                onDetailClick = { idAset ->
                    navController.navigate("${DestinasiDetailAset.route}/$idAset")
                },
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(DestinasiInsertAset.route) {
            InsertAsetView(navigateBack = { navController.popBackStack() })
        }
        composable(
            DestinasiDetailAset.routesWithArg,
            arguments = listOf(navArgument(DestinasiDetailAset.ID_ASET) {
                type = NavType.StringType
            })
        ) {
            val idAset = it.arguments?.getString(DestinasiDetailAset.ID_ASET)
            idAset?.let { id ->
                DetailAsetView(
                    navigateToItemUpdate = { navController.navigate("${DestinasiUpdateAset.route}/$id") },
                    navigateBack = { navController.popBackStack() }
                )
            }
        }
        composable(
            DestinasiUpdateAset.routesWithArg,
            arguments = listOf(navArgument(DestinasiUpdateAset.ID_ASET) {
                type = NavType.StringType
            })
        ) {
            UpdateAsetView(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.popBackStack() }
            )
        }

        // Halaman Kategori
        composable(DestinasiHomeKategori.route) {
            HomeKategoriView(
                navigateToItemEntry = { navController.navigate(DestinasiInsertKategori.route) },
                onDetailClick = { idKategori ->
                    navController.navigate("${DestinasiDetailKategori.route}/$idKategori")
                },
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(DestinasiInsertKategori.route) {
            InsertKategoriView(navigateBack = { navController.popBackStack() })
        }
        composable(
            DestinasiDetailKategori.routesWithArg,
            arguments = listOf(navArgument(DestinasiDetailKategori.ID_KATEGORI) {
                type = NavType.StringType
            })
        ) {
            val idKategori = it.arguments?.getString(DestinasiDetailKategori.ID_KATEGORI)
            idKategori?.let { id ->
                DetailKategoriView(
                    navigateToItemUpdate = { navController.navigate("${DestinasiUpdateKategori.route}/$id") },
                    navigateBack = { navController.popBackStack() }
                )
            }
        }
        composable(
            DestinasiUpdateKategori.routesWithArg,
            arguments = listOf(navArgument(DestinasiUpdateKategori.ID_KATEGORI) {
                type = NavType.StringType
            })
        ) {
            UpdateKategoriView(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.popBackStack() }
            )
        }

        // Halaman Pendapatan
        composable(DestinasiHomePendapatan.route) {
            HomePendapatanView(
                navigateToItemEntry = { navController.navigate(DestinasiInsertPendapatan.route) },
                onDetailClick = { idPendapatan ->
                    navController.navigate("${DestinasiDetailPendapatan.route}/$idPendapatan")
                },
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(DestinasiInsertPendapatan.route) {
            InsertPendapatanView(navigateBack = { navController.popBackStack() })
        }
        composable(
            DestinasiDetailPendapatan.routesWithArg,
            arguments = listOf(navArgument(DestinasiDetailPendapatan.ID_PENDAPATAN) {
                type = NavType.StringType
            })
        ) {
            val idPendapatan = it.arguments?.getString(DestinasiDetailPendapatan.ID_PENDAPATAN)
            idPendapatan?.let { id ->
                DetailPendapatanView(
                    navigateToItemUpdate = { navController.navigate("${DestinasiUpdatePendapatan.route}/$id") },
                    navigateBack = { navController.popBackStack() }
                )
            }
        }
        composable(
            DestinasiUpdatePendapatan.routesWithArg,
            arguments = listOf(navArgument(DestinasiUpdatePendapatan.ID_PENDAPATAN) {
                type = NavType.StringType
            })
        ) {
            UpdatePendapatanView(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.popBackStack() }
            )
        }

        // Halaman Pengeluaran
        composable(DestinasiHomePengeluaran.route) {
            HomePengeluaranView(
                navigateToItemEntry = { navController.navigate(DestinasiInsertPengeluaran.route) },
                onDetailClick = { idPengeluaran ->
                    navController.navigate("${DestinasiDetailPengeluaran.route}/$idPengeluaran")
                },
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(DestinasiInsertPengeluaran.route) {
            InsertPengeluaranView(navigateBack = { navController.popBackStack() })
        }
        composable(
            DestinasiDetailPengeluaran.routesWithArg,
            arguments = listOf(navArgument(DestinasiDetailPengeluaran.ID_PENGELUARAN) {
                type = NavType.StringType
            })
        ) {
            val idPengeluaran = it.arguments?.getString(DestinasiDetailPengeluaran.ID_PENGELUARAN)
            idPengeluaran?.let { id ->
                DetailPengeluaranView(
                    navigateToItemUpdate = { navController.navigate("${DestinasiUpdatePengeluaran.route}/$id") },
                    navigateBack = { navController.popBackStack() }
                )
            }
        }
        composable(
            DestinasiUpdatePengeluaran.routesWithArg,
            arguments = listOf(navArgument(DestinasiUpdatePengeluaran.ID_PENGELUARAN) {
                type = NavType.StringType
            })
        ) {
            UpdatePengeluaranView(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.popBackStack() }
            )
        }
    }
}


