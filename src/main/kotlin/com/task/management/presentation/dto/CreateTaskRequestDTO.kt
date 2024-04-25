package com.task.management.presentation.dto

data class CreateTaskRequestDTO(
    val title: String,
    val description: String,
    val status: String
)