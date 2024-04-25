package com.task.management.domain.model

data class Report(
    val period: String,
    val numberOfTasksCompleted: Int,
    val averageTimeToCompleteTasks: Double
)