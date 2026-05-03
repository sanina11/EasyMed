package pt.ipc.medicapp.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import java.time.LocalDate
import java.util.concurrent.atomic.AtomicLong

/**
 * Repositório em memória. Sobrevive enquanto o processo estiver vivo.
 * Pré-carrega alguns dados-exemplo para o protótipo IPC.
 */
object MedicRepository {

    private val medIdSeq = AtomicLong(0)
    private val tomaIdSeq = AtomicLong(0)

    val medicacoes = mutableStateListOf<Medicacao>()
    val historico  = mutableStateListOf<RegistoToma>()
    val utilizador = mutableStateOf(Utilizador("João Silva", "joao.s@gmail.com"))

    init { seed() }

    private fun seed() {
        adicionar("Paracetamol", "500mg", "08:00", Frequencia.DIARIO)
        adicionar("Ibuprofeno",  "400mg", "13:00", Frequencia.DIARIO)
        adicionar("Gabapentina", "300mg", "16:30", Frequencia.DIARIO)

        // Histórico de exemplo (dois dias anteriores)
        val hoje = LocalDate.now()
        listOf(
            Triple("Paracetamol", "08:00", hoje.minusDays(1)),
            Triple("Ibuprofeno",  "13:00", hoje.minusDays(1)),
            Triple("Paracetamol", "08:00", hoje.minusDays(2)),
        ).forEach { (n, h, d) ->
            historico.add(
                RegistoToma(
                    id = tomaIdSeq.incrementAndGet(),
                    medicacaoId = medicacoes.first { it.nome == n }.id,
                    nome = n, hora = h, data = d, tomado = true
                )
            )
        }
    }

    fun adicionar(nome: String, dosagem: String, hora: String, freq: Frequencia): Medicacao {
        val m = Medicacao(medIdSeq.incrementAndGet(), nome, dosagem, hora, freq)
        medicacoes.add(m)
        return m
    }

    fun getById(id: Long): Medicacao? = medicacoes.firstOrNull { it.id == id }

    /** Marca uma medicação como tomada hoje. */
    fun marcarTomado(medId: Long) {
        val m = getById(medId) ?: return
        val hoje = LocalDate.now()
        val ja = historico.any { it.medicacaoId == medId && it.data == hoje && it.tomado }
        if (!ja) {
            historico.add(
                RegistoToma(
                    id = tomaIdSeq.incrementAndGet(),
                    medicacaoId = medId,
                    nome = m.nome,
                    hora = m.hora,
                    data = hoje,
                    tomado = true
                )
            )
        }
    }

    fun foiTomadaHoje(medId: Long): Boolean {
        val hoje = LocalDate.now()
        return historico.any { it.medicacaoId == medId && it.data == hoje && it.tomado }
    }

    fun atualizarUtilizador(nome: String, email: String) {
        utilizador.value = Utilizador(nome, email)
    }
}
