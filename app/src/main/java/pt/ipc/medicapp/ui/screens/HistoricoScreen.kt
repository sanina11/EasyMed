package pt.ipc.medicapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.CheckCircle
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
import pt.ipc.medicapp.data.RegistoToma
import pt.ipc.medicapp.ui.components.AppBottomBar
import pt.ipc.medicapp.ui.components.BottomTab
import pt.ipc.medicapp.ui.theme.Success
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun HistoricoScreen(
    onIrInicio: () -> Unit,
    onIrPerfil: () -> Unit,
) {
    val regs = MedicRepository.historico
    // Agrupar por data, descendente
    val porData = regs
        .sortedWith(compareByDescending<RegistoToma> { it.data }.thenBy { it.hora })
        .groupBy { it.data }

    Scaffold(
        bottomBar = {
            AppBottomBar(
                selecionado = BottomTab.HISTORICO,
                onInicio    = onIrInicio,
                onHistorico = { /* já estás aqui */ },
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
                "Histórico",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                "Tomas registadas",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(20.dp))

            if (porData.isEmpty()) {
                Text(
                    "Ainda não há registos. Marca uma medicação como tomada para começar.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    porData.forEach { (data, lista) ->
                        item(key = data.toString()) {
                            DataHeader(data)
                        }
                        items(lista, key = { it.id }) { reg ->
                            ItemHistorico(reg)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DataHeader(data: LocalDate) {
    val formatter = DateTimeFormatter.ofPattern("d MMMM", Locale("pt", "PT"))
    val hoje = LocalDate.now()
    val rotulo = when (data) {
        hoje              -> "Hoje"
        hoje.minusDays(1) -> "Ontem"
        else              -> data.format(formatter)
    }
    Text(
        rotulo,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun ItemHistorico(reg: RegistoToma) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Filled.CheckCircle,
                contentDescription = null,
                tint = Success
            )
            Spacer(Modifier.size(12.dp))
            Text(
                "${reg.nome} – ${reg.hora}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
