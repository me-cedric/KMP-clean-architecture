package cedricmeyer.kmp.presentation.view.features.tasks

import cedricmeyer.kmp.domain.interactors.GetTasksUseCase
import cedricmeyer.kmp.presentation.model.ResourceUIState
import cedricmeyer.kmp.presentation.intent.BaseViewModel
import cafe.adriel.voyager.core.model.screenModelScope
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch

class TasksViewModel(
    private val getTasksUseCase: GetTasksUseCase,
) : BaseViewModel<TasksContrant.Event, TasksContrant.State, TasksContrant.Effect>() {
    init {
        getTasks()
    }

    override fun createInitialState(): TasksContrant.State =
        TasksContrant.State(tasks = ResourceUIState.Idle)

    override fun handleEvent(event: TasksContrant.Event) {
        when (event) {
            TasksContrant.Event.OnTryCheckAgainClick -> getTasks()
            is TasksContrant.Event.OnTaskClick -> setEffect {
                TasksContrant.Effect.NavigateToDetailTask(
                    event.idTask
                )
            }
            is TasksContrant.Event.OnAddTaskClick -> setEffect {
                TasksContrant.Effect.NavigatedToInsert
            }

            TasksContrant.Event.OnFavoritesClick -> setEffect { TasksContrant.Effect.NavigateToFavorites }


        }
    }
    private fun getTasks() {
        setState { copy(tasks = ResourceUIState.Loading) }
        screenModelScope.launch {
            getTasksUseCase(Unit)
                .onSuccess {
                    Napier.d("Loaded Tasks", null, "getTasksUseCase")
                    setState {
                        copy(
                            tasks = if (it.isEmpty())
                                ResourceUIState.Empty
                            else
                                ResourceUIState.Success(it)
                        )
                    }
                }
                .onFailure {
                    Napier.e("Failed to load tasks", it, "getTasksUseCase")
                    setState { copy(tasks = ResourceUIState.Error()) }
                }
        }
    }
}