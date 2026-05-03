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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Vaccines
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pt.ipc.medicapp.data.MedicRepository
import pt.ipc.medicapp.ui.theme.Success

@Composable
fun DetalheMedicacaoScreen(
    medId: Long,
    onVoltar: () -> Unit,
) {
    val med = MedicRepository.getById(medId)
    if (med == null) {
        // medicação removida ou id inválido
        Box(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            contentAlignment = Alignment.Center
        ) { Text("Medicação não encontrada.") }
        return
    }

    var mostrarConfirmar by remember { mutableStateOf(false) }
    // Estado é derivado do repositório — recompõe ao tomar
    val tomado = MedicRepository.foiTomadaHoje(med.id)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ) {
        Spacer(Modifier.height(24.dp))
        Text(
            med.nome,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            shape = RoundedCornerShape(14.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                LinhaInfo(Icons.Filled.Vaccines, "Dosagem", med.dosagem)
                Spacer(Modifier.height(10.dp))
                LinhaInfo(Icons.Filled.AccessTime, "Hora", med.hora)
                Spacer(Modifier.height(10.dp))
                LinhaInfo(Icons.Filled.Repeat, "Frequência", med.frequencia.label)
            }
        }

        Spacer(Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (tomado) MaterialTheme.colorScheme.primaryContainer
                    else MaterialTheme.colorScheme.surface,
                    RoundedCornerShape(12.dp)
                )
                .padding(14.dp)
        ) {
            Icon(
                if (tomado) Icons.Filled.CheckCircle else Icons.Filled.Cancel,
                contentDescription = null,
                tint = if (tomado) Success else MaterialTheme.colorScheme.error
            )
            Spacer(Modifier.size(10.dp))
            Text(
                "Estado: " + if (tomado) "Tomado" else "Não tomado",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = { mostrarConfirmar = true },
            enabled = !tomado,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp)
        ) { Text(if (tomado) "Já tomado hoje" else "Marcar como tomado") }

        Spacer(Modifier.height(12.dp))

        OutlinedButton(
            onClick = onVoltar,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(14.dp)
        ) { Text("Voltar") }
    }

    if (mostrarConfirmar) {
        AlertDialog(
            onDismissRequest = { mostrarConfirmar = false },
            icon = {
                Icon(
                    Icons.Filled.Vaccines,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            title = { Text("Confirmar toma") },
            text  = { Text("Confirmar toma da medicação ${med.nome}?") },
            confirmButton = {
                Button(onClick = {
                    MedicRepository.marcarTomado(med.id)
                    mostrarConfirmar = false
                }) { Text("Confirmar") }
            },
            dismissButton = {
                TextButton(onClick = { mostrarConfirmar = false }) { Text("Cancelar") }
            }
        )
    }
}

@Composable
private fun LinhaInfo(icone: ImageVector, rotulo: String, valor: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icone, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.size(10.dp))
        Text(
            "$rotulo: ",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(valor, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
    }
}
