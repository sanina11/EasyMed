package pt.ipc.easymed.ui.screens

import pt.ipc.easymed.ui.components.BottomNavigationBar
import pt.ipc.easymed.data.Medicamento
import pt.ipc.easymed.data.MedicationRepository
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun DashboardScreen(
    onAdicionarClick: () -> Unit,
    onMedicamentoClick: (Medicamento) -> Unit,
    onInicioClick: () -> Unit,
    onHistoricoClick: () -> Unit,
    onPerfilClick: () -> Unit
) {
    val hoje = LocalDate.now()
        .format(DateTimeFormatter.ofPattern("'Hoje, 'dd MMM", Locale("pt")))

    val medicamentos = MedicationRepository.getMedicamentos()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selected = "inicio",
                onInicioClick = onInicioClick,
                onHistoricoClick = onHistoricoClick,
                onPerfilClick = onPerfilClick
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
        ) {
            Text(
                text = hoje,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (medicamentos.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Nenhum medicamento agendado para hoje.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    LazyColumn(modifier = Modifier.padding(8.dp)) {
                        items(medicamentos) { med ->
                            MedicamentoItem(
                                medicamento = med,
                                onClick = { onMedicamentoClick(med) },
                                onTomadoToggle = {
                                    MedicationRepository.alternarTomado(med.id)
                                }
                            )
                            if (med != medicamentos.last()) {
                                HorizontalDivider()
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedButton(
                onClick = onAdicionarClick,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Adicionar Medicação")
            }
        }
    }
}

@Composable
fun MedicamentoItem(
    medicamento: Medicamento,
    onClick: () -> Unit,
    onTomadoToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = if (medicamento.dosagem.isNotEmpty())
                    "${medicamento.nome} ${medicamento.dosagem}"
                else medicamento.nome,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = medicamento.hora,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
        if (medicamento.tomado) {
            IconButton(onClick = onTomadoToggle) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Tomado",
                    tint = Color(0xFF4CAF50)
                )
            }
        } else {
            RadioButton(
                selected = false,
                onClick = onTomadoToggle
            )
        }
    }
}