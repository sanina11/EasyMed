package pt.ipc.medicapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import pt.ipc.medicapp.ui.screens.AdicionarMedicacaoScreen
import pt.ipc.medicapp.ui.screens.DashboardScreen
import pt.ipc.medicapp.ui.screens.DetalheMedicacaoScreen
import pt.ipc.medicapp.ui.screens.HistoricoScreen
import pt.ipc.medicapp.ui.screens.LoginScreen
import pt.ipc.medicapp.ui.screens.PerfilScreen
import pt.ipc.medicapp.ui.screens.RegistarScreen
import pt.ipc.medicapp.ui.screens.SplashScreen

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
                onEntrar   = { nav.popBackStack() }
            )
        }

        composable(Routes.DASHBOARD) {
            DashboardScreen(
                onAdicionar  = { nav.navigate(Routes.ADICIONAR) },
                onAbrirItem  = { id -> nav.navigate(Routes.detalhe(id)) },
                onIrHistorico= { nav.navigate(Routes.HISTORICO) },
                onIrPerfil   = { nav.navigate(Routes.PERFIL) },
            )
        }

        composable(Routes.ADICIONAR) {
            AdicionarMedicacaoScreen(
                onGuardar  = { nav.popBackStack() },
                onCancelar = { nav.popBackStack() }
            )
        }

        composable(
            Routes.DETALHE,
            arguments = listOf(navArgument("medId") { type = NavType.LongType })
        ) { entry ->
            val id = entry.arguments?.getLong("medId") ?: 0L
            DetalheMedicacaoScreen(
                medId    = id,
                onVoltar = { nav.popBackStack() }
            )
        }

        composable(Routes.HISTORICO) {
            HistoricoScreen(
                onIrInicio = {
                    nav.navigate(Routes.DASHBOARD) {
                        popUpTo(Routes.DASHBOARD) { inclusive = true }
                    }
                },
                onIrPerfil = { nav.navigate(Routes.PERFIL) }
            )
        }

        composable(Routes.PERFIL) {
            PerfilScreen(
                onIrInicio    = {
                    nav.navigate(Routes.DASHBOARD) {
                        popUpTo(Routes.DASHBOARD) { inclusive = true }
                    }
                },
                onIrHistorico = { nav.navigate(Routes.HISTORICO) },
                onTerminarSessao = {
                    nav.navigate(Routes.LOGIN) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}
