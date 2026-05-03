package pt.ipc.easymed.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pt.ipc.easymed.ui.screens.LoginScreen
import pt.ipc.easymed.ui.screens.RegistarScreen
import pt.ipc.easymed.ui.screens.SplashScreen

@Composable
fun AppNavGraph() {
    val nav = rememberNavController()

    NavHost(navController = nav, startDestination = Routes.LOGIN) {

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
                    // TODO: navegar para Dashboard quando o ecrã for criado
                },
                onRegistar = { nav.navigate(Routes.REGISTAR) }
            )
        }

        composable(Routes.REGISTAR) {
            RegistarScreen(
                onRegistar = { nav.popBackStack() },
                onEntrar   = { nav.popBackStack() }
            )
        }
    }
}