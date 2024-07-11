package cedricmeyer.kmp.domain.interactors

import kotlinx.coroutines.CoroutineDispatcher
import cedricmeyer.kmp.domain.models.Task
import cedricmeyer.kmp.domain.IRepository
import cedricmeyer.kmp.domain.interactors.type.BaseUseCase

class GetTaskUseCase(

    private val repository: IRepository,
    dispatcher: CoroutineDispatcher
): BaseUseCase<Int, Task>(dispatcher) {
    override suspend fun block(param: Int): Task = repository.getTask(param)
}