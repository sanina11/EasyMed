package pt.ipc.easymed.data

import androidx.compose.runtime.mutableStateListOf

data class Medicamento(
    val id: Int,
    val nome: String,
    val dosagem: String,
    val hora: String,
    val tomado: Boolean = false
)

data class HistoricoToma(
    val data: String,
    val nome: String,
    val dosagem: String,
    val hora: String,
    val tomado: Boolean
)

object MedicationRepository {
    private val _medicamentos = mutableStateListOf(
        Medicamento(1, "Paracetamol", "500mg", "08:00", tomado = true),
        Medicamento(2, "Ibuprofeno", "", "13:00"),
        Medicamento(3, "Gabapentina", "", "16:30")
    )

    private val _historico = mutableStateListOf(
        HistoricoToma("Ontem, 21 Jun", "Paracetamol", "500mg", "08:00", true),
        HistoricoToma("Ontem, 21 Jun", "Ibuprofeno", "", "13:00", false),
        HistoricoToma("20 Jun", "Paracetamol", "500mg", "08:00", true),
        HistoricoToma("20 Jun", "Gabapentina", "", "16:30", true)
    )

    fun getMedicamentos(): List<Medicamento> {
        return _medicamentos
    }

    fun getHistorico(): List<HistoricoToma> {
        return _historico
    }

    fun adicionarMedicamento(nome: String, dosagem: String, hora: String) {
        val novoId = (_medicamentos.maxOfOrNull { it.id } ?: 0) + 1
        _medicamentos.add(Medicamento(novoId, nome, dosagem, hora))
    }

    fun alternarTomado(id: Int) {
        val index = _medicamentos.indexOfFirst { it.id == id }
        if (index != -1) {
            val med = _medicamentos[index]
            _medicamentos[index] = med.copy(tomado = !med.tomado)
        }
    }

    fun eliminarMedicamento(id: Int) {
        _medicamentos.removeAll { it.id == id }
    }
}
