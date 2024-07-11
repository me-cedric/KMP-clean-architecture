package cedricmeyer.kmp.domain.models

import cedricmeyer.kmp.domain.models.Complexity
import cedricmeyer.kmp.domain.models.Status

data class Task(
    val id: Long,
    val name_task: String,
    val description: String,
    val assigned_from: String,
    val assigned_to: String,
    val status: Status,
    val complexity: Complexity,
)

