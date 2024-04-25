package com.task.management.presentation.controller

import com.task.management.exceptions.ResourceNotFoundException
import jakarta.persistence.EntityNotFoundException
import org.apache.coyote.BadRequestException
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.server.NotAcceptableStatusException

open class BaseController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    open fun handleValidationExceptions(ex: MethodArgumentNotValidException): Map<String, Any> {
        val errors = HashMap<String, Any>()
        val messageStr = StringBuilder()
        for (error in ex.bindingResult.allErrors) {
            val errorMessage = error.defaultMessage
            messageStr.append(errorMessage)
        }
        errors["success"] = false
        errors["message"] = messageStr.toString()
        return errors
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException::class)
    open fun handleValidationExceptions(ex: EntityNotFoundException): Map<String, Any> {
        val errors = HashMap<String, Any>()
        errors["success"] = false
        errors["message"] = "Record Not Found"
        return errors
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(NotAcceptableStatusException::class)
    open fun handleValidationExceptions(ex: NotAcceptableStatusException): Map<String, Any> {
        val errors = HashMap<String, Any>()
        errors["success"] = false
        errors["message"] = ex.reason.toString()
        return errors
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException::class)
    open fun handleBadRequestException(ex: BadRequestException): Map<String, Any> {
        val errors = HashMap<String, Any>()
        errors["success"] = false
        errors["message"] = ex.message.toString()
        return errors
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException::class)
    open fun handleBadRequestException(ex: HttpMessageNotReadableException): Map<String, Any> {
        val errors = HashMap<String, Any>()
        errors["success"] = false
        errors["message"] = ex.localizedMessage
        return errors
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(Exception::class)
    open fun handleException(ex: Exception): Map<String, Any> {
        val errors = HashMap<String, Any>()
        errors["success"] = false
        errors["message"] = ex.message.toString()
        // errors.put("message", "Something was wrong. Please contact administrator.")
        ex.printStackTrace()
        return errors
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException::class)
    open fun handleResourceNotFoundException(ex: ResourceNotFoundException): Map<String, Any> {
        val errors = HashMap<String, Any>()
        errors["success"] = false
        errors["message"] = ex.message.toString()
        return errors
    }
}
