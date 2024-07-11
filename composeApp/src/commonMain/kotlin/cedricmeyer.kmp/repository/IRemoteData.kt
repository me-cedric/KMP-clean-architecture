package cedricmeyer.kmp.repository

import cedricmeyer.kmp.domain.models.Task

interface IRemoteData {
    suspend fun getTasksFromApi(): List<Task>
    suspend fun getTaskFromApi(id: Int): Task
    suspend fun addTask(task: Task) : Boolean
}