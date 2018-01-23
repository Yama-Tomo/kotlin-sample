package com.yamatomo.cleanarch

import java.text.SimpleDateFormat
import java.util.Calendar
import javax.servlet.http.HttpServletRequest

import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.*
import org.springframework.web.HttpRequestMethodNotSupportedException

import com.yamatomo.cleanarch.usecase.exception.DataNotFoundException
import com.yamatomo.cleanarch.usecase.exception.InvalidParamsException

@RestControllerAdvice
class ExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidParamsException::class)
    fun badRequest(): HashMap<String, Any> {
        val response = HashMap<String, Any>()
        response.put("message", "Bad request")
        response.put("status", HttpStatus.BAD_REQUEST.value())
        return response
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DataNotFoundException::class)
    fun notFound(): HashMap<String, Any> {
        val response = HashMap<String, Any>()
        response.put("message", "Not found")
        response.put("status", HttpStatus.NOT_FOUND.value())
        return response
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun methodNotAllowed(): HashMap<String, Any> {
        val response = HashMap<String, Any>()
        response.put("message", "Mehotd not allowed")
        response.put("status", HttpStatus.METHOD_NOT_ALLOWED.value())
        return response
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun internalServerError(e: Exception, req: HttpServletRequest): HashMap<String, Any> {
        val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS")

        System.err.println("ERROR(500) " + (sdf.format((Calendar.getInstance()).getTime()))
            + ": remoteAddr:" + req.getRemoteAddr()
            + ", remoteHost:" + req.getRemoteHost()
            + ", requestURL:" + req.getRequestURL()
        )
        e.printStackTrace()

        val response = HashMap<String, Any>()
        response.put("message", "Internal server error")
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value())
        return response
    }
}
