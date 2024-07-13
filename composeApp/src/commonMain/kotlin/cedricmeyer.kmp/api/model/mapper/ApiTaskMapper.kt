package cedricmeyer.kmp.api.model.mapper

import cedricmeyer.kmp.api.model.Item
import cedricmeyer.kmp.domain.models.Task
import cedricmeyer.kmp.domain.models.Complexity
import cedricmeyer.kmp.domain.models.Status
import cedricmeyer.kmp.domain.models.map.Mapper


class ApiTaskMapper : Mapper<Item, Task>() {
    override fun map(model: Item): Task = model.run {
        Task(
            id.toLong(), name_task, description, assigned_from,assigned_to,
            when (status) {
                0 -> Status.PENDING
                1 -> Status.DONE
                else -> {
                    Status.UNKNOWN
                }
            },
            when (complexity) {
                1 -> Complexity.EASY
                2 -> Complexity.MEDIUM
                3 -> Complexity.HARD
                else -> {
                    Complexity.UNKNOWN
                }
            },
        )
    }


    override fun inverseMap(model: Task): Item = model.run {
        Item(
            id.toInt(), name_task, description,
            assigned_from, assigned_to,
            when (status) {
                Status.PENDING -> 0
                Status.DONE -> 1
                else -> {
                    Status.UNKNOWN as Int
                }
            },
            when (complexity) {
                Complexity.EASY -> 1
                Complexity.MEDIUM -> 2
                Complexity.HARD -> 3
                else -> {
                    Complexity.UNKNOWN as Int
                }
            }
        )
    }
}

