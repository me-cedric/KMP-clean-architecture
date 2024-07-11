package cedricmeyer.kmp.domain.interactors

import kotlinx.coroutines.CoroutineDispatcher
import cedricmeyer.kmp.domain.IRepository
import cedricmeyer.kmp.domain.interactors.type.BaseUseCase


class IsTaskFavoriteUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher
): BaseUseCase<Int,Boolean>(dispatcher) {
    override suspend fun block(param: Int): Boolean = repository.isTaskFavorite(param)

}