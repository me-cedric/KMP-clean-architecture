package cedricmeyer.kmp.repository


import kotlinx.coroutines.flow.Flow
import cedricmeyer.kmp.domain.models.Task

interface ICacheData {
    suspend fun addTaskToFavorite(task: Task)
    suspend fun removeTaskFromFavorite(idTask: Int)
    suspend fun getAllTaskFavorites(): Flow<List<Task>>
    suspend fun isTaskFavorite(idTask: Int): Boolean
}