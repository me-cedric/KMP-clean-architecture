package cedricmeyer.kmp

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import cedricmeyer.kmp.di.initKoin

fun main() = application {
    initKoin {}
    Window(
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(width = 400.dp, height = 800.dp),
        title = "KMP-clean-architecture",
    ) {
        App()
    }
}