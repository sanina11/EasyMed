package pt.ipc.easymed.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun BottomNavigationBar(
    selected: String,
    onInicioClick: () -> Unit = {},
    onHistoricoClick: () -> Unit = {},
    onPerfilClick: () -> Unit = {}
) {
    NavigationBar {
        NavigationBarItem(
            selected = selected == "inicio",
            onClick = onInicioClick,
            icon = { Icon(Icons.Default.Home, contentDescription = "Início") },
            label = { Text("Início") }
        )
        NavigationBarItem(
            selected = selected == "historico",
            onClick = onHistoricoClick,
            icon = { Icon(Icons.Default.List, contentDescription = "Histórico") },
            label = { Text("Histórico") }
        )
        NavigationBarItem(
            selected = selected == "perfil",
            onClick = onPerfilClick,
            icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") }
        )
    }
}