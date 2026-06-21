package pt.ipc.easymed.data

import androidx.compose.runtime.mutableStateListOf

data class Medicamento(
    val id: Int,
    val nome: String,
    val dosagem: String,
    val hora: String,
    val tomado: Boolean = false
)

object MedicationRepository {
    private val _medicamentos = mutableStateListOf(
        Medicamento(1, "Paracetamol", "500mg", "08:00", tomado = true),
        Medicamento(2, "Ibuprofeno", "", "13:00"),
        Medicamento(3, "Gabapentina", "", "16:30")
    )

    fun getMedicamentos(): List<Medicamento> {
        return _medicamentos
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
