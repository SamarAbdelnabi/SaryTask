package com.android.sary.network

import com.android.sary.network.Result.Companion.ERROR_CODE_NO_INTERNET_CONNECTION
import okhttp3.Headers


/**
 * A generic class that holds a value with its loading status.
 *
 * Result is usually created by the Repository classes where they return
 * `LiveData<Result<T>>` to pass back the latest data to the UI with its fetch status.
 */

data class Result<out T>(
    val status: Status,
    val data: T?= null,
    val header: Headers?,
    val message: String?,
    val error: Error? = null
) {
    fun isSuccessful(): Boolean = status == Status.SUCCESS

    fun isNoInternetIssue() : Boolean {
        return error?.isNoInternetIssue() == true
    }

    fun isBusinessError() : Boolean {
        return error?.isBusinessError() == true
    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        const val ERROR_CODE_NO_INTERNET_CONNECTION = -300

        fun <T> success(data: T?, header: Headers? = null): Result<T> {
            return Result(
                status = Status.SUCCESS,
                header = header,
                data = data,
                error = null,
                message = null
            )
        }

        fun <T> error(
            message: String,
            header: Headers? = null,
            data: T? = null,
            code: Int = 0,
            statusCode: Int = 0,
            responseMessage: String = ""
        ): Result<T> {
            return Result(
                status = Status.ERROR,
                header = header,
                data = data,
                message = message,
                error = Error(code, message, statusCode, responseMessage)
            )
        }

        fun <T> loading(header: Headers? = null, data: T? = null): Result<T> {
            return Result(Status.LOADING, data = data, header = header, message = null)
        }
    }
}

data class Error(
    val code: Int = 0,
    val message: String = "",
    val statusCode: Int = 0,
    val responseMessage: String = ""
) {

    fun isBusinessError() : Boolean {
        return ! isNoInternetIssue() && code > 0 && code != 200 && code != 500
    }

    fun isNoInternetIssue() : Boolean {
        return code == ERROR_CODE_NO_INTERNET_CONNECTION
    }
}