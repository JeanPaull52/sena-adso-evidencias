package com.example.noteplusadmin.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.noteplusadmin.ui.screens.CrearDocenteScreen
import com.example.noteplusadmin.ui.screens.CrearPersonaScreen
import com.example.noteplusadmin.ui.screens.DashboardScreen
import com.example.noteplusadmin.ui.screens.LoginConstraintScreen
import com.example.noteplusadmin.ui.screens.LoginScreen
import com.example.noteplusadmin.ui.screens.NacionalidadScreen

sealed class Screen(val route: String) {
    data object Login            : Screen("login")
    data object LoginConstraint  : Screen("login_constraint")
    data object Dashboard        : Screen("dashboard")
    data object Nacionalidad     : Screen("nacionalidad")
    data object CrearPersona     : Screen("crear_persona")
    data object CrearDocente     : Screen("crear_docente/{idPersona}") {
        fun withArgs(idPersona: Int) = "crear_docente/$idPersona"
    }
}

@Composable
fun NotePlusNavGraph(navController: NavHostController) {
    NavHost(
        navController    = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.LoginConstraint.route) {
            LoginConstraintScreen(navController = navController)
        }
        composable(Screen.Dashboard.route) {
            DashboardScreen(navController = navController)
        }
        composable(Screen.Nacionalidad.route) {
            NacionalidadScreen(navController = navController)
        }
        composable(Screen.CrearPersona.route) {
            CrearPersonaScreen(navController = navController)
        }
        composable(
            route     = Screen.CrearDocente.route,
            arguments = listOf(navArgument("idPersona") { type = NavType.IntType })
        ) { backStack ->
            CrearDocenteScreen(
                navController = navController,
                idPersona     = backStack.arguments?.getInt("idPersona") ?: 0
            )
        }
    }
}
