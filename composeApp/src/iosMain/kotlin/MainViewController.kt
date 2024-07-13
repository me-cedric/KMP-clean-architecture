import androidx.compose.ui.window.ComposeUIViewController
import cedricmeyer.kmp.App
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

fun MainViewController() = ComposeUIViewController {
    Napier.base(DebugAntilog())
    App()
}