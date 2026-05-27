package pt.ipc.easymed.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pt.ipc.easymed.ui.screens.LoginScreen
import pt.ipc.easymed.ui.screens.RegistarScreen
import pt.ipc.easymed.ui.screens.SplashScreen
import pt.ipc.easymed.ui.screens.DashboardScreen

@Composable
fun AppNavGraph() {
    val nav = rememberNavController()

    NavHost(navController = nav, startDestination = Routes.SPLASH) {

        composable(Routes.SPLASH) {
            SplashScreen(onContinuar = {
                nav.navigate(Routes.LOGIN) {
                    popUpTo(Routes.SPLASH) { inclusive = true }
                }
            })
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                onEntrar = {
                    nav.navigate(Routes.DASHBOARD) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onRegistar = { nav.navigate(Routes.REGISTAR) }
            )
        }

        composable(Routes.REGISTAR) {
            RegistarScreen(
                onRegistar = { nav.popBackStack() },
                onEntrar = { nav.popBackStack() }
            )
        }

        composable(Routes.DASHBOARD) {
            DashboardScreen(
                onAdicionarClick = { /* TODO */ },
                onMedicamentoClick = { /* TODO */ }
            )
        }

    }
}