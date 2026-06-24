package pt.ipc.easymed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import pt.ipc.easymed.data.MedicationRepository
import pt.ipc.easymed.navigation.AppNavGraph
import pt.ipc.easymed.ui.theme.MedicAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MedicationRepository.init(applicationContext)
        enableEdgeToEdge()
        setContent {
            MedicAppTheme {
                AppNavGraph()
            }
        }
    }
}
