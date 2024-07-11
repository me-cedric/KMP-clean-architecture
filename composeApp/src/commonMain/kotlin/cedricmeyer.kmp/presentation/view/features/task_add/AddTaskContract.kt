package cedricmeyer.kmp.presentation.view.features.task_add

import cedricmeyer.kmp.domain.models.Task
import cedricmeyer.kmp.presentation.model.ResourceUIState
import cedricmeyer.kmp.presentation.intent.UIEffect
import cedricmeyer.kmp.presentation.intent.UIEvent
import cedricmeyer.kmp.presentation.intent.UIState

interface AddTaskContract {
    sealed interface Event : UIEvent {
        object OnBackPressed : Event
        data class OnTaskAddClick(val task: Task) : Event

    }

    data class State(
        val task: ResourceUIState<Task>,
        val addedTask: ResourceUIState<Boolean>,
    ) : UIState

    sealed interface Effect : UIEffect {
        object TaskAdded : Effect
        object BackNavigation : Effect
    }
}