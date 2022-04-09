package com.android.sary.data.entity

import com.google.gson.annotations.SerializedName

data class ErrorModel(
    @SerializedName("error")
    val message: String?,
    @SerializedName("code")
    val code: String?,
    @SerializedName("Status")
    val status: String?
) {
    fun getErrorMessage(): String? {
        return if (!message.isNullOrBlank()) {
            message
        } else {
            null
        }
    }
    fun getCodeOrStatusCode(): Int? = code?.toIntOrNull()?: status?.toIntOrNull()
}