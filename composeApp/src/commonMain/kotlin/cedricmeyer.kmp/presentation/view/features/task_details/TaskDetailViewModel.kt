package cedricmeyer.kmp.presentation.view.features.task_details

import cedricmeyer.kmp.domain.interactors.GetTaskUseCase
import cedricmeyer.kmp.domain.interactors.IsTaskFavoriteUseCase
import cedricmeyer.kmp.domain.interactors.SwitchTaskFavoriteUseCase
import cedricmeyer.kmp.presentation.model.ResourceUIState
import cedricmeyer.kmp.presentation.intent.BaseViewModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch

class TaskDetailViewModel(
    private val getTaskUseCase: GetTaskUseCase,
    private val isTaskFavoriteUseCase: IsTaskFavoriteUseCase,
    private val switchTaskFavoriteUseCase: SwitchTaskFavoriteUseCase,
    private val taskid: Int,

    ) :
    BaseViewModel<TaskDetailContract.Event, TaskDetailContract.State, TaskDetailContract.Effect>() {

    init {
        getTask(taskid)
        checkIfIsFavorite(taskid)
    }

    override fun createInitialState(): TaskDetailContract.State =
        TaskDetailContract.State(
            task = ResourceUIState.Idle,
            isFavorite = ResourceUIState.Idle,
        )


    override fun handleEvent(event: TaskDetailContract.Event) {
        when (event) {
            TaskDetailContract.Event.OnFavoriteClick -> SwitchTaskFavorite(taskid)
            TaskDetailContract.Event.OnTryCheckAgainClick -> getTask(taskid)
            TaskDetailContract.Event.OnBackPressed -> setEffect { TaskDetailContract.Effect.BackNavigation }
        }
    }

    private fun getTask(taskId: Int) {
        setState { copy(task = ResourceUIState.Loading) }
        screenModelScope.launch {
            getTaskUseCase(taskId)
                .onSuccess { setState { copy(task = ResourceUIState.Success(it)) } }
                .onFailure { setState { copy(task = ResourceUIState.Error()) } }
        }
    }

    private fun checkIfIsFavorite(idTask: Int) {
        setState { copy(isFavorite = ResourceUIState.Loading) }
        screenModelScope.launch {
            isTaskFavoriteUseCase(idTask)
                .onSuccess { setState { copy(isFavorite = ResourceUIState.Success(it)) } }
                .onFailure { setState { copy(isFavorite = ResourceUIState.Error()) } }
        }
    }

    private fun SwitchTaskFavorite(idTask: Int) {
        setState { copy(isFavorite = ResourceUIState.Loading) }
        screenModelScope.launch {
            switchTaskFavoriteUseCase(idTask)
                .onSuccess {
                    setState { copy(isFavorite = ResourceUIState.Success(it)) }
                    setEffect { TaskDetailContract.Effect.TaskAdded }
                }
                .onFailure { setState { copy(isFavorite = ResourceUIState.Error()) } }
        }
    }

}