package cedricmeyer.kmp.presentation.view.features.task_add

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import cedricmeyer.kmp.domain.models.Task
import cedricmeyer.kmp.domain.interactors.AddTaskUseCase
import cedricmeyer.kmp.presentation.intent.BaseViewModel
import cedricmeyer.kmp.presentation.model.ResourceUIState

class AddTaskViewModel(
    private val addTaskUseCase: AddTaskUseCase,
) : BaseViewModel<AddTaskContract.Event, AddTaskContract.State, AddTaskContract.Effect>() {

    override fun createInitialState(): AddTaskContract.State =
        AddTaskContract.State(
            task = ResourceUIState.Idle,
            addedTask = ResourceUIState.Idle
        )
    override fun handleEvent(event: AddTaskContract.Event) {
        when (event) {
            is AddTaskContract.Event.OnTaskAddClick -> AddTask(event.task)
            AddTaskContract.Event.OnBackPressed -> setEffect { AddTaskContract.Effect.BackNavigation }
            else -> {}
        }
    }

    private fun AddTask(task1: Task) {
        setState { copy(task = ResourceUIState.Loading) }
        screenModelScope.launch {
            addTaskUseCase(task1)
                .onSuccess {
                    setState { copy(addedTask=ResourceUIState.Success(it))  }
                    setEffect { AddTaskContract.Effect.TaskAdded }
                }
                .onFailure { setState { copy(task = ResourceUIState.Error()) } }
        }
    }
}