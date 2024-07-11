package cedricmeyer.kmp.sqldelight

import app.cash.sqldelight.coroutines.asFlow
import cedricmeyer.kmp.domain.models.Complexity
import cedricmeyer.kmp.domain.models.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import cedricmeyer.kmp.domain.models.Task
import cedricmeyer.kmp.repository.ICacheData

class CacheDataImp(
    private val sharedDatabase: SharedDatabase,
) : ICacheData {

    override suspend fun addTaskToFavorite(task: Task) {
        sharedDatabase {
            it.appDatabaseQueries.insertTaskFavorite(
                task.id,
                task.name_task,
                task.description,
                task.assigned_to,
                task.assigned_from,
                task.status,
                task.complexity,
            )
        }
    }

    override suspend fun removeTaskFromFavorite(idTask: Int) {
        sharedDatabase {
            it.appDatabaseQueries.removeTaskFavorite(idTask.toLong())
        }
    }

    override suspend fun getAllTaskFavorites(): Flow<List<Task>> =
        sharedDatabase { appDatabase ->
            appDatabase.appDatabaseQueries.selectAllTaskFavorite(::mapFavorite).asFlow()
                .map { it.executeAsList() }
        }

    override suspend fun isTaskFavorite(idTask: Int): Boolean =
        sharedDatabase {
            it.appDatabaseQueries.selectTaskFavoriteById(idTask.toLong()).executeAsOne()
        }

    private fun mapFavorite(
        id: Long,
        name_task: String,
        description: String,
        assigned_to: String,
        assigned_from: String,
        status: Status,
        complexity: Complexity,
    ): Task = Task(id, name_task, description,assigned_to, assigned_from, status,  complexity)
}