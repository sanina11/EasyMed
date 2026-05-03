package pt.ipc.medicapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pt.ipc.medicapp.data.MedicRepository
import pt.ipc.medicapp.ui.components.AppBottomBar
import pt.ipc.medicapp.ui.components.BottomTab

@Composable
fun PerfilScreen(
    onIrInicio: () -> Unit,
    onIrHistorico: () -> Unit,
    onTerminarSessao: () -> Unit,
) {
    val user by MedicRepository.utilizador
    var editando by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            AppBottomBar(
                selecionado = BottomTab.PERFIL,
                onInicio    = onIrInicio,
                onHistorico = onIrHistorico,
                onPerfil    = { /* já estás aqui */ }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(16.dp))
            Text(
                "Perfil",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(24.dp))

            // Avatar circular
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(56.dp)
                )
            }
            Spacer(Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    LinhaPerfil(Icons.Filled.Person, "Nome",  user.nome)
                    Spacer(Modifier.height(12.dp))
                    LinhaPerfil(Icons.Filled.Email,  "Email", user.email)
                }
            }

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = { editando = true },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Icon(Icons.Filled.Edit, contentDescription = null)
                Spacer(Modifier.size(8.dp))
                Text("Editar perfil")
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = onTerminarSessao,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Icon(Icons.Filled.Logout, contentDescription = null)
                Spacer(Modifier.size(8.dp))
                Text("Terminar sessão")
            }
        }
    }

    if (editando) {
        DialogoEditarPerfil(
            nomeAtual = user.nome,
            emailAtual = user.email,
            onConfirmar = { nome, email ->
                MedicRepository.atualizarUtilizador(nome, email)
                editando = false
            },
            onCancelar = { editando = false }
        )
    }
}

@Composable
private fun LinhaPerfil(icone: androidx.compose.ui.graphics.vector.ImageVector, rotulo: String, valor: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icone, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.size(10.dp))
        Column {
            Text(rotulo,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(valor, style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun DialogoEditarPerfil(
    nomeAtual: String,
    emailAtual: String,
    onConfirmar: (String, String) -> Unit,
    onCancelar: () -> Unit,
) {
    var nome  by remember { mutableStateOf(nomeAtual) }
    var email by remember { mutableStateOf(emailAtual) }
    AlertDialog(
        onDismissRequest = onCancelar,
        title = { Text("Editar perfil") },
        text = {
            Column {
                OutlinedTextField(
                    value = nome, onValueChange = { nome = it },
                    label = { Text("Nome") }, singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = email, onValueChange = { email = it },
                    label = { Text("Email") }, singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = { onConfirmar(nome.trim(), email.trim()) }) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancelar) { Text("Cancelar") }
        }
    )
}
