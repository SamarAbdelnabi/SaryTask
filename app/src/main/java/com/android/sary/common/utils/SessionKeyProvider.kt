package com.android.sary.common.utils

import com.android.sary.local.SecurePreferences
import javax.inject.Inject
import javax.inject.Singleton

interface KeyProvider<T> {
    fun get(): T
}

@Singleton
class SessionKeyProvider @Inject constructor(private val storePreferences: SecurePreferences) :
    KeyProvider<String> {

    override fun get(): String {
        val accessToken = storePreferences.getAccessToken()
        return if (accessToken != null) {
            "token $accessToken"
        } else {
            ""
        }
    }
}