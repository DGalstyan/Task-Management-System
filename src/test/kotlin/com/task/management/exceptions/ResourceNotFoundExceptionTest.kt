package com.task.management.exceptions

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ResourceNotFoundExceptionTest {

    @Test
    fun testExceptionMessage() {
        val exception = ResourceNotFoundException("Resource", "id", 123)
        val expectedMessage = "Resource not found with id : '123'"
        assertEquals(expectedMessage, exception.message)
    }

    @Test
    fun testExceptionThrowing() {
        val exception = assertThrows<ResourceNotFoundException> {
            throw ResourceNotFoundException("Resource", "id", 123)
        }
        assertEquals("Resource not found with id : '123'", exception.message)
    }
}
