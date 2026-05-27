package pt.ipc.easymed.ui.screens
import pt.ipc.easymed.ui.components.BottomNavigationBar
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

data class Medicamento(
    val id: Int,
    val nome: String,
    val dosagem: String,
    val hora: String,
    var tomado: Boolean = false
)

@Composable
fun DashboardScreen(
    onAdicionarClick: () -> Unit,
    onMedicamentoClick: (Medicamento) -> Unit
) {
    val hoje = LocalDate.now()
        .format(DateTimeFormatter.ofPattern("'Hoje, 'dd MMM", Locale("pt")))

    var medicamentos by remember {
        mutableStateOf(
            listOf(
                Medicamento(1, "Paracetamol", "500mg", "08:00", tomado = true),
                Medicamento(2, "Ibuprofeno", "", "13:00"),
                Medicamento(3, "Gabapentina", "", "16:30")
            )
        )
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(selected = "inicio")
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

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                LazyColumn(modifier = Modifier.padding(8.dp)) {
                    items(medicamentos) { med ->
                        MedicamentoItem(
                            medicamento = med,
                            onClick = { onMedicamentoClick(med) }
                        )
                        if (med != medicamentos.last()) {
                            HorizontalDivider()
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
                Text("+ Adicionar")
            }
        }
    }
}

@Composable
fun MedicamentoItem(
    medicamento: Medicamento,
    onClick: () -> Unit
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
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Tomado",
                tint = Color(0xFF4CAF50)
            )
        } else {
            RadioButton(
                selected = false,
                onClick = { onClick() }
            )
        }
    }
}