package com.xyinc.poi.locator.controller.advice

import com.xyinc.poi.locator.dto.ApiErrorResponse
import com.xyinc.poi.locator.exception.EntityNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class ExceptionHandlerAdvice {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFound(ex: EntityNotFoundException): ResponseEntity<ApiErrorResponse> {
        log.info("Handling EntityNotFoundException instance {}", ex)
        val apiErrorResponse = ApiErrorResponse(
            error = ex.message!!
        )

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiErrorResponse)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<ApiErrorResponse> {
        log.info("Handling IllegalArgumentException instance {}", ex)
        val apiErrorResponse = ApiErrorResponse(
            error = ex.message!!
        )

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrorResponse)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ApiErrorResponse> {
        log.info("Handling general Exception instance [{}]", ex)
        val apiErrorResponse = ApiErrorResponse(
            error = ex.message!!
        )

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiErrorResponse)
    }
}
