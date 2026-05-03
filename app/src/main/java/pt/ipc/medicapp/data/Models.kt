package pt.ipc.medicapp.data

import java.time.LocalDate

enum class Frequencia(val label: String) {
    DIARIO("Diário"),
    SEMANAL("Semanal"),
    PERSONALIZADO("Personalizado");

    companion object {
        fun fromLabel(label: String): Frequencia =
            entries.firstOrNull { it.label == label } ?: DIARIO
    }
}

/** Uma medicação configurada pelo utilizador. */
data class Medicacao(
    val id: Long,
    val nome: String,
    val dosagem: String,
    val hora: String,            // ex: "08:00"
    val frequencia: Frequencia,
)

/** Registo de uma toma — usado no histórico. */
data class RegistoToma(
    val id: Long,
    val medicacaoId: Long,
    val nome: String,
    val hora: String,
    val data: LocalDate,
    val tomado: Boolean,
)

data class Utilizador(
    val nome: String,
    val email: String,
)
