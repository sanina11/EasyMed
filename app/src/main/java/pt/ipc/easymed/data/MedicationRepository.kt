package pt.ipc.easymed.data

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

data class Medicamento(
    val id: Int,
    val nome: String,
    val dosagem: String,
    val hora: String,
    val tomado: Boolean = false,
    val frequencia: String = ""
)

data class HistoricoToma(
    val data: String,
    val nome: String,
    val dosagem: String,
    val hora: String,
    val tomado: Boolean
)

object MedicationRepository {
    private lateinit var database: AppDatabase
    private val _medicamentos = mutableStateListOf<Medicamento>()
    private val _historico = mutableStateListOf<HistoricoToma>()

    fun init(context: Context) {
        database = AppDatabase.getDatabase(context)
        CoroutineScope(Dispatchers.IO).launch {
            val dbMeds = database.medicationDao().getMedicamentosList()
            val dbHist = database.medicationDao().getHistoricoList()
            withContext(Dispatchers.Main) {
                _medicamentos.clear()
                if (dbMeds.isEmpty()) {
                    val defaults = listOf(
                        Medicamento(1, "Paracetamol", "500mg", "08:00", tomado = true, frequencia = "Diário"),
                        Medicamento(2, "Ibuprofeno", "", "13:00", frequencia = "8h/8h"),
                        Medicamento(3, "Gabapentina", "", "16:30", frequencia = "Diário")
                    )
                    _medicamentos.addAll(defaults)
                    CoroutineScope(Dispatchers.IO).launch {
                        defaults.forEach { database.medicationDao().insertMedicamento(it.toEntity()) }
                    }
                } else {
                    _medicamentos.addAll(dbMeds.map { it.toDomain() })
                }

                _historico.clear()
                if (dbHist.isEmpty()) {
                    val defaultHist = listOf(
                        HistoricoToma("Ontem, 21 Jun", "Paracetamol", "500mg", "08:00", true),
                        HistoricoToma("Ontem, 21 Jun", "Ibuprofeno", "", "13:00", false),
                        HistoricoToma("20 Jun", "Paracetamol", "500mg", "08:00", true),
                        HistoricoToma("20 Jun", "Gabapentina", "", "16:30", true)
                    )
                    _historico.addAll(defaultHist)
                    CoroutineScope(Dispatchers.IO).launch {
                        defaultHist.forEach { database.medicationDao().insertHistorico(it.toEntity()) }
                    }
                } else {
                    _historico.addAll(dbHist.map { it.toDomain() })
                }
            }
        }
    }

    fun getMedicamentos(): List<Medicamento> {
        return _medicamentos
    }

    fun getHistorico(): List<HistoricoToma> {
        return _historico
    }

    fun adicionarMedicamento(nome: String, dosagem: String, hora: String, frequencia: String) {
        val novoId = (_medicamentos.maxOfOrNull { it.id } ?: 0) + 1
        val novo = Medicamento(novoId, nome, dosagem, hora, frequencia = frequencia)
        _medicamentos.add(novo)
        CoroutineScope(Dispatchers.IO).launch {
            database.medicationDao().insertMedicamento(novo.toEntity())
        }
    }

    fun alternarTomado(id: Int) {
        val index = _medicamentos.indexOfFirst { it.id == id }
        if (index != -1) {
            val med = _medicamentos[index]
            val novoMed = med.copy(tomado = !med.tomado)
            _medicamentos[index] = novoMed

            // Se foi marcado como tomado, adicionamos ao histórico dinamicamente
            if (novoMed.tomado) {
                val formatter = SimpleDateFormat("dd MMM", Locale("pt"))
                val hojeStr = "Hoje, " + formatter.format(Calendar.getInstance().time)
                val histEntry = HistoricoToma(hojeStr, novoMed.nome, novoMed.dosagem, novoMed.hora, true)
                _historico.add(0, histEntry)
                CoroutineScope(Dispatchers.IO).launch {
                    database.medicationDao().insertHistorico(histEntry.toEntity())
                }
            }

            CoroutineScope(Dispatchers.IO).launch {
                database.medicationDao().insertMedicamento(novoMed.toEntity())
            }
        }
    }

    fun eliminarMedicamento(id: Int) {
        _medicamentos.removeAll { it.id == id }
        CoroutineScope(Dispatchers.IO).launch {
            database.medicationDao().deleteMedicamento(id)
        }
    }
}
