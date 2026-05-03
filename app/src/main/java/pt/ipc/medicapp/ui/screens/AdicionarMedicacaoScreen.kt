package pt.ipc.medicapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pt.ipc.medicapp.data.Frequencia
import pt.ipc.medicapp.data.MedicRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdicionarMedicacaoScreen(
    onGuardar: () -> Unit,
    onCancelar: () -> Unit,
) {
    var nome     by remember { mutableStateOf("") }
    var dosagem  by remember { mutableStateOf("") }
    var hora     by remember { mutableStateOf("") }
    var freq     by remember { mutableStateOf(Frequencia.DIARIO) }
    var aberto   by remember { mutableStateOf(false) }
    var erro     by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ) {
        Spacer(Modifier.height(24.dp))
        Text(
            "Adicionar medicação",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome do medicamento") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = dosagem,
            onValueChange = { dosagem = it },
            label = { Text("Dosagem (ex: 500mg)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = hora,
            onValueChange = { hora = it },
            label = { Text("Hora (ex: 08:00)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))

        ExposedDropdownMenuBox(
            expanded = aberto,
            onExpandedChange = { aberto = !aberto }
        ) {
            OutlinedTextField(
                value = freq.label,
                onValueChange = {},
                readOnly = true,
                label = { Text("Frequência") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = aberto) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            DropdownMenu(
                expanded = aberto,
                onDismissRequest = { aberto = false },
                modifier = Modifier.background(MaterialTheme.colorScheme.surface)
            ) {
                Frequencia.entries.forEach { f ->
                    DropdownMenuItem(
                        text = { Text(f.label) },
                        onClick = {
                            freq = f
                            aberto = false
                        }
                    )
                }
            }
        }

        erro?.let {
            Spacer(Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                erro = if (nome.isBlank() || dosagem.isBlank() || hora.isBlank())
                    "Preenche todos os campos."
                else {
                    MedicRepository.adicionar(nome.trim(), dosagem.trim(), hora.trim(), freq)
                    onGuardar()
                    null
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp)
        ) { Text("Guardar") }

        Spacer(Modifier.height(12.dp))

        OutlinedButton(
            onClick = onCancelar,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(14.dp)
        ) { Text("Cancelar") }
    }
}
