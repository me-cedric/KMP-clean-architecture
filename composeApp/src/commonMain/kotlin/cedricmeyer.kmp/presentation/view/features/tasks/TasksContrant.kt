package cedricmeyer.kmp.presentation.view.features.tasks

import cedricmeyer.kmp.domain.models.Task
import cedricmeyer.kmp.presentation.model.ResourceUIState
import cedricmeyer.kmp.presentation.intent.UIEffect
import cedricmeyer.kmp.presentation.intent.UIEvent
import cedricmeyer.kmp.presentation.intent.UIState

interface TasksContrant {
    sealed interface Event : UIEvent {
        object OnTryCheckAgainClick : Event
        object OnFavoritesClick : Event
        data class OnTaskClick(val idTask: Int) : Event

        object OnAddTaskClick : Event
    }

    data class State(
        val tasks: ResourceUIState<List<Task>>
    ) : UIState

    sealed interface Effect : UIEffect {
        data class NavigateToDetailTask(val idTask: Int) : Effect
        object NavigateToFavorites : Effect

        object NavigatedToInsert : Effect
    }
}