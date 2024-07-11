package cedricmeyer.kmp

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cedricmeyer.kmp.presentation.view.theme.AppTheme
import cedricmeyer.kmp.presentation.view.features.tasks.TaskScreen

@Composable
internal fun App() = AppTheme {
    Navigator(TaskScreen())
}
