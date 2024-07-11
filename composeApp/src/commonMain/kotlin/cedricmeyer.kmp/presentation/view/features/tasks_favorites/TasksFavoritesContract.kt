package cedricmeyer.kmp.presentation.view.features.tasks_favorites

import cedricmeyer.kmp.domain.models.Task
import cedricmeyer.kmp.presentation.model.ResourceUIState
import cedricmeyer.kmp.presentation.intent.UIEffect
import cedricmeyer.kmp.presentation.intent.UIEvent
import cedricmeyer.kmp.presentation.intent.UIState

interface TasksFavoritesContract {
    sealed interface Event: UIEvent{
        object OnBackPressed: Event
        object OnTryCheckAgainClick : Event
        data class OnTaskClick(val idTask: Int): Event
    }

    data class State(
        val taskFavorites: ResourceUIState<List<Task>>
    ): UIState

    sealed interface Effect :UIEffect{
        data class NavigateToDetailTask(val idTask: Int) : Effect
        object BackNavigation:Effect
    }
}