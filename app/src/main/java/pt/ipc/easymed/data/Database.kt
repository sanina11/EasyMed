package pt.ipc.easymed.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update

@Entity(tableName = "medicamentos")
data class MedicamentoEntity(
    @PrimaryKey val id: Int,
    val nome: String,
    val dosagem: String,
    val hora: String,
    val tomado: Boolean
)

@Entity(tableName = "historico_toma")
data class HistoricoTomaEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val data: String,
    val nome: String,
    val dosagem: String,
    val hora: String,
    val tomado: Boolean
)

fun MedicamentoEntity.toDomain() = Medicamento(id, nome, dosagem, hora, tomado)
fun Medicamento.toEntity() = MedicamentoEntity(id, nome, dosagem, hora, tomado)

fun HistoricoTomaEntity.toDomain() = HistoricoToma(data, nome, dosagem, hora, tomado)
fun HistoricoToma.toEntity() = HistoricoTomaEntity(data = data, nome = nome, dosagem = dosagem, hora = hora, tomado = tomado)

@Dao
interface MedicationDao {
    @Query("SELECT * FROM medicamentos")
    suspend fun getMedicamentosList(): List<MedicamentoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedicamento(medicamento: MedicamentoEntity)

    @Query("DELETE FROM medicamentos WHERE id = :id")
    suspend fun deleteMedicamento(id: Int)

    @Query("SELECT * FROM historico_toma ORDER BY id DESC")
    suspend fun getHistoricoList(): List<HistoricoTomaEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistorico(historico: HistoricoTomaEntity)
}

@Database(entities = [MedicamentoEntity::class, HistoricoTomaEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun medicationDao(): MedicationDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "easymed_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
