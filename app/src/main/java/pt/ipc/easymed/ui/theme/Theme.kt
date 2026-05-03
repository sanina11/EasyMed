package pt.ipc.easymed.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary       = Teal700,
    onPrimary     = Surface,
    primaryContainer = Teal100,
    onPrimaryContainer = Teal700,
    secondary     = TealAccent,
    onSecondary   = Surface,
    background    = Background,
    onBackground  = OnSurface,
    surface       = Surface,
    onSurface     = OnSurface,
    surfaceVariant = Background,
    onSurfaceVariant = OnSurfaceMut,
    outline       = Outline,
    error         = Danger,
)

@Composable
fun MedicAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography  = AppTypography,
        content     = content
    )
}
