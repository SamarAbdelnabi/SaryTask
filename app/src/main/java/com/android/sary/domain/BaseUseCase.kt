package com.android.sary.domain

import android.app.Application
import com.android.sary.R
import com.android.sary.common.utils.isConnectedOrConnecting
import com.android.sary.data.entity.ErrorModel
import com.android.sary.local.SecurePreferences
import retrofit2.Response
import javax.inject.Inject
import com.android.sary.network.Result
import com.android.sary.network.Result.Companion.ERROR_CODE_NO_INTERNET_CONNECTION
import com.google.gson.Gson
import timber.log.Timber
import java.net.UnknownHostException

abstract class BaseUseCase {

    @Inject
    lateinit var appContext: Application

    @Inject
    open lateinit var securePreferences: SecurePreferences

    protected suspend fun <T> getResult(
        call: suspend () -> Response<T>
    ): Result<T> {
        return try {
            /**
             * if there is no internet connection, dont make the call and throw an error direct
             */
            if (!appContext.isConnectedOrConnecting()) {
                return Result.error(
                    message = appContext.getString(R.string.error_handler_no_internet_connection),
                    code = ERROR_CODE_NO_INTERNET_CONNECTION
                )
            }
            val response = call()
            if (response.isSuccessful) {
                Result.success(header = response.headers(), data = response.body())
            } else {
                handleErrorResult(
                    response = response
                )
            }
        } catch (e: Exception) {
            return handleExceptionErrorResult(e)
        }
    }

    private fun <T> handleErrorResult(
        response: Response<T>
    ): Result<T> {

        val defaultErrorMessage = appContext.getString(R.string.error_handler_something_went_wrong)

        return try {
            val errorModel =
                Gson().fromJson(response.errorBody()?.charStream(), ErrorModel::class.java)

            error(
                message = errorModel.getErrorMessage()
                    ?: defaultErrorMessage,
                code = errorModel.getCodeOrStatusCode() ?: response.code(),
                header = response.headers(),
                statusCode = errorModel.status?.toIntOrNull() ?: 0,
                responseMessage = errorModel.getErrorMessage() ?: defaultErrorMessage
            )
        } catch (e: RuntimeException) {
            error(defaultErrorMessage, code = response.code())
        }
    }

    private fun <T> handleExceptionErrorResult(e: Exception): Result<T> {
        return when (e) {
            is UnknownHostException -> {
                return error(appContext.getString(R.string.error_handler_no_internet_connection),
                    ERROR_CODE_NO_INTERNET_CONNECTION
                )
            }
            else -> {
                Timber.w(e)
                error(appContext.getString(R.string.error_handler_something_went_wrong))
            }
        }
    }


    private fun <T> error(
        message: String,
        code: Int = 0,
        statusCode: Int = 0,
        header: okhttp3.Headers?=null,
        responseMessage: String = ""

    ): Result<T> {
        Timber.e(message)
        return error(
            message = message,
            code = code,
            statusCode = statusCode,
            responseMessage = responseMessage ,
            header = header
        )
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