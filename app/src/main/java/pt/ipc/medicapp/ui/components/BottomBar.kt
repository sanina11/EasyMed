package pt.ipc.medicapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

enum class BottomTab { INICIO, HISTORICO, PERFIL }

@Composable
fun AppBottomBar(
    selecionado: BottomTab,
    onInicio: () -> Unit,
    onHistorico: () -> Unit,
    onPerfil: () -> Unit,
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp
    ) {
        NavigationBarItem(
            selected = selecionado == BottomTab.INICIO,
            onClick  = onInicio,
            icon     = { Icon(Icons.Filled.Home, contentDescription = "Início") },
            label    = { Text("Início") },
            colors   = barItemColors()
        )
        NavigationBarItem(
            selected = selecionado == BottomTab.HISTORICO,
            onClick  = onHistorico,
            icon     = { Icon(Icons.Filled.History, contentDescription = "Histórico") },
            label    = { Text("Histórico") },
            colors   = barItemColors()
        )
        NavigationBarItem(
            selected = selecionado == BottomTab.PERFIL,
            onClick  = onPerfil,
            icon     = { Icon(Icons.Filled.Person, contentDescription = "Perfil") },
            label    = { Text("Perfil") },
            colors   = barItemColors()
        )
    }
}

@Composable
private fun barItemColors() = NavigationBarItemDefaults.colors(
    selectedIconColor   = MaterialTheme.colorScheme.primary,
    selectedTextColor   = MaterialTheme.colorScheme.primary,
    indicatorColor      = MaterialTheme.colorScheme.primaryContainer,
    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
)
