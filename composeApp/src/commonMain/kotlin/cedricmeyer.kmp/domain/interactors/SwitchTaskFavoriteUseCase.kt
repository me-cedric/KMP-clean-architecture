package cedricmeyer.kmp.domain.interactors

import kotlinx.coroutines.CoroutineDispatcher
import cedricmeyer.kmp.domain.IRepository
import cedricmeyer.kmp.domain.interactors.type.BaseUseCase

class SwitchTaskFavoriteUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher
) : BaseUseCase<Int, Boolean>(dispatcher) {
    override suspend fun block(param: Int): Boolean {
        if (repository.isTaskFavorite(param)) {
            repository.removeTaskFromFavorite(param)
        } else {
            repository.addTaskToFavorites(repository.getTask(param))
        }
        return repository.isTaskFavorite(param)
    }
}