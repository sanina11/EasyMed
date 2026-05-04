package pt.ipc.easymed.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import pt.ipc.easymed.R

@Composable
fun SplashScreen(onContinuar: () -> Unit) {
    LaunchedEffect(Unit) {                  // ⭐ NOVO
        delay(1500)
        onContinuar()
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo EasyMed",
            modifier = Modifier.size(200.dp)
        )
    }
}