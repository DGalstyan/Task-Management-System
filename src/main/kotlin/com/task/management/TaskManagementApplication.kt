package com.task.management

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TaskManagementApplication

fun main(args: Array<String>) {
	runApplication<TaskManagementApplication>(*args)
}
