package cedricmeyer.kmp.domain.interactors

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import cedricmeyer.kmp.domain.models.Task
import cedricmeyer.kmp.domain.IRepository
import cedricmeyer.kmp.domain.interactors.type.BaseUseCaseFlow

class GetTasksFavoritesUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
): BaseUseCaseFlow<Unit, List<Task>>(dispatcher) {
    override suspend fun build(param: Unit): Flow<List<Task>> = repository.getTasksFavorites()

}