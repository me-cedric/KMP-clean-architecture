package cedricmeyer.kmp.presentation.view.features.task_details

import cedricmeyer.kmp.domain.models.Task
import cedricmeyer.kmp.presentation.model.ResourceUIState
import cedricmeyer.kmp.presentation.intent.UIEffect
import cedricmeyer.kmp.presentation.intent.UIEvent
import cedricmeyer.kmp.presentation.intent.UIState

interface TaskDetailContract {
    sealed interface Event : UIEvent {
        object OnFavoriteClick : Event
        object OnTryCheckAgainClick : Event
        object OnBackPressed : Event
    }

    data class State(
        val task: ResourceUIState<Task>,
        val isFavorite: ResourceUIState<Boolean>,
    ) : UIState

    sealed interface Effect : UIEffect {
        object TaskAdded : Effect
        object TaskRemoved : Effect
        object BackNavigation : Effect
    }
}