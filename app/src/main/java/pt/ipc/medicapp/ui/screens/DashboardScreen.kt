package pt.ipc.medicapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pt.ipc.medicapp.data.MedicRepository
import pt.ipc.medicapp.data.Medicacao
import pt.ipc.medicapp.ui.components.AppBottomBar
import pt.ipc.medicapp.ui.components.BottomTab
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun DashboardScreen(
    onAdicionar: () -> Unit,
    onAbrirItem: (Long) -> Unit,
    onIrHistorico: () -> Unit,
    onIrPerfil: () -> Unit,
) {
    val meds = MedicRepository.medicacoes
    val hoje = "Hoje, " + LocalDate.now()
        .format(DateTimeFormatter.ofPattern("d MMM", Locale("pt", "PT")))

    Scaffold(
        bottomBar = {
            AppBottomBar(
                selecionado = BottomTab.INICIO,
                onInicio    = { /* já estás aqui */ },
                onHistorico = onIrHistorico,
                onPerfil    = onIrPerfil
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(Modifier.height(16.dp))
            Text(
                hoje,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                "As tuas medicações de hoje",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(20.dp))

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(meds, key = { it.id }) { med ->
                    MedicacaoCard(
                        med = med,
                        tomado = MedicRepository.foiTomadaHoje(med.id),
                        onClick = { onAbrirItem(med.id) },
                        onToggleTomado = { MedicRepository.marcarTomado(med.id) }
                    )
                }
            }

            Spacer(Modifier.height(12.dp))
            Button(
                onClick = onAdicionar,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = null)
                Spacer(Modifier.size(8.dp))
                Text("Adicionar")
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun MedicacaoCard(
    med: Medicacao,
    tomado: Boolean,
    onClick: () -> Unit,
    onToggleTomado: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(14.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.MedicalServices,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(Modifier.size(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "${med.nome} ${med.dosagem}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    med.hora,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clickable { onToggleTomado() },
                contentAlignment = Alignment.Center
            ) {
                if (tomado) {
                    Icon(
                        Icons.Filled.CheckCircle,
                        contentDescription = "Tomado",
                        tint = MaterialTheme.colorScheme.primary
                    )
                } else {
                    Icon(
                        Icons.Outlined.RadioButtonUnchecked,
                        contentDescription = "Marcar como tomado",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
