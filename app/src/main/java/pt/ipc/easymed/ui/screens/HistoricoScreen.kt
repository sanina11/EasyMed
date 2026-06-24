package pt.ipc.easymed.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.ipc.easymed.data.HistoricoToma
import pt.ipc.easymed.data.MedicationRepository
import pt.ipc.easymed.ui.components.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoricoScreen(
    onInicioClick: () -> Unit,
    onHistoricoClick: () -> Unit,
    onPerfilClick: () -> Unit
) {
    val historico = MedicationRepository.getHistorico()
    // Agrupa o histórico por data, mantendo a ordem original da lista
    val historicoAgrupado = historico.groupBy { it.data }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Histórico de Tomas",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selected = "historico",
                onInicioClick = onInicioClick,
                onHistoricoClick = onHistoricoClick,
                onPerfilClick = onPerfilClick
            )
        }
    ) { paddingValues ->
        if (historico.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Ainda não existem registos de toma.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                historicoAgrupado.forEach { (data, itens) ->
                    item {
                        Text(
                            text = data,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                    items(itens) { item ->
                        HistoricoItemCard(item = item)
                    }
                }
            }
        }
    }
}

@Composable
fun HistoricoItemCard(item: HistoricoToma) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (item.dosagem.isNotEmpty()) "${item.nome} ${item.dosagem}" else item.nome,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
                Text(
                    text = item.hora,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            if (item.tomado) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Tomado",
                        tint = Color(0xFF2E7D32) // Success Green
                    )
                    Text(
                        text = "Tomado",
                        color = Color(0xFF2E7D32),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Não tomado",
                        tint = Color(0xFFC62828) // Danger Red
                    )
                    Text(
                        text = "Não tomado",
                        color = Color(0xFFC62828),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
