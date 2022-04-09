package com.android.sary.local

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SecurePreferences @Inject constructor(@ApplicationContext context: Context) {

    companion object {
        private const val ACCESS_TOKEN = "pref_access_token"
        private const val CURRENT_LANGUAGE = "pref_current_language"
    }

    private val sharedPreference by lazy {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        EncryptedSharedPreferences.create(
            "secure_shared_preference", masterKeyAlias, context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun setAccessToken(accessToken: String?) {
        sharedPreference.edit().putString(ACCESS_TOKEN, accessToken).apply()
    }

    fun getAccessToken(): String? {
        val token = sharedPreference.getString(ACCESS_TOKEN, null)
        Timber.d("Access Token: $token")
        return token
    }

    fun setLanguage(language: String?) {
        sharedPreference.edit().putString(CURRENT_LANGUAGE, language).apply()
    }

    fun getLanguage(): String? {
        val language = sharedPreference.getString(CURRENT_LANGUAGE, "ar")
        return language
    }


}