package pt.ipc.easymed.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import pt.ipc.easymed.ui.screens.LoginScreen
import pt.ipc.easymed.ui.screens.RegistarScreen
import pt.ipc.easymed.ui.screens.SplashScreen
import pt.ipc.easymed.ui.screens.DashboardScreen
import pt.ipc.easymed.ui.screens.AdicionarScreen
import pt.ipc.easymed.ui.screens.DetalheScreen
import pt.ipc.easymed.data.MedicationRepository

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
                onAdicionarClick = { nav.navigate(Routes.ADICIONAR) },
                onMedicamentoClick = { med ->
                    nav.navigate(Routes.detalhe(med.id.toLong()))
                }
            )
        }

        composable(Routes.ADICIONAR) {
            AdicionarScreen(
                onGuardar = { nome, dosagem, hora ->
                    MedicationRepository.adicionarMedicamento(nome, dosagem, hora)
                    nav.popBackStack()
                },
                onVoltar = { nav.popBackStack() }
            )
        }

        composable(
            route = Routes.DETALHE,
            arguments = listOf(navArgument("medId") { type = NavType.IntType })
        ) { backStackEntry ->
            val medId = backStackEntry.arguments?.getInt("medId") ?: 0
            DetalheScreen(
                medId = medId,
                onVoltar = { nav.popBackStack() },
                onEliminado = { nav.popBackStack() }
            )
        }

    }
}
