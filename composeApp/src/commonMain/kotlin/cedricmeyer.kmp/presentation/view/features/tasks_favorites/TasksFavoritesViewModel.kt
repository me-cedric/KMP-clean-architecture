package cedricmeyer.kmp.presentation.view.features.tasks_favorites

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import cedricmeyer.kmp.domain.interactors.GetTasksFavoritesUseCase
import cedricmeyer.kmp.presentation.model.ResourceUIState
import cedricmeyer.kmp.presentation.intent.BaseViewModel

class TasksFavoritesViewModel(
    private val getTasksFavoritesUseCase: GetTasksFavoritesUseCase,
) : BaseViewModel<TasksFavoritesContract.Event,TasksFavoritesContract.State,TasksFavoritesContract.Effect>() {

    init {
        getTasksFavorites()
    }

    override fun createInitialState(): TasksFavoritesContract.State =
        TasksFavoritesContract.State(
            taskFavorites = ResourceUIState.Idle
        )

    override fun handleEvent(event: TasksFavoritesContract.Event) {
        when(event){
            TasksFavoritesContract.Event.OnTryCheckAgainClick -> getTasksFavorites()
            is TasksFavoritesContract.Event.OnTaskClick ->
                setEffect { TasksFavoritesContract.Effect.NavigateToDetailTask(event.idTask) }
            TasksFavoritesContract.Event.OnBackPressed ->
                setEffect { TasksFavoritesContract.Effect.BackNavigation }
        }
    }

    private fun getTasksFavorites(){
        setState { copy(taskFavorites= ResourceUIState.Loading) }

        screenModelScope.launch {
            getTasksFavoritesUseCase(Unit).collect{
                it.onSuccess {
                    setState {
                        copy(
                            taskFavorites =
                                if(it.isEmpty())
                            ResourceUIState.Empty
                            else
                            ResourceUIState.Success(it)
                        )
                    }
                }.onFailure { setState { copy(taskFavorites=ResourceUIState.Error()) } }
            }
        }
    }


}