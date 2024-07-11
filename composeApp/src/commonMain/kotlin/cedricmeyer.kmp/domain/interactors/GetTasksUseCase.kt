package cedricmeyer.kmp.domain.interactors

import cedricmeyer.kmp.domain.IRepository
import cedricmeyer.kmp.domain.interactors.type.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import cedricmeyer.kmp.domain.models.Task


class GetTasksUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<Unit, List<Task>>(dispatcher) {
    override suspend fun block(param: Unit): List<Task> = repository.getTasks()
}