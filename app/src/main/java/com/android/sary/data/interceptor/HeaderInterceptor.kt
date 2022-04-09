package com.android.sary.data.interceptor

import android.content.Context
import com.android.sary.common.utils.APP_VERSION
import com.android.sary.common.utils.CURRENT_LANGUAGE
import com.android.sary.common.utils.PLATFORM
import com.android.sary.common.utils.SessionKeyProvider
import com.android.sary.local.SecurePreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeaderInterceptor @Inject constructor(
    private val sessionKeyProvider: SessionKeyProvider,
//    @ApplicationContext private val context: Context,
//    private val securePreferences: SecurePreferences
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", sessionKeyProvider.get())
            .header("Content-Type", "application/json")
            .addHeader("Platform", PLATFORM)
            .addHeader("App-Version", APP_VERSION)
            .addHeader("Accept-Language", CURRENT_LANGUAGE)
            .build()

        val response = safeProceed(request, chain)

        Timber.d(
            "[REQUEST-HEADERS] ---> \n${
                response.networkResponse()?.request()?.headers()
            }\n[RESPONSE-HEADERS] ---> \n${response.networkResponse()?.headers()}"
        )
        return response
    }

    fun safeProceed(request: Request, chain: Interceptor.Chain): Response{
        return chain.proceed(request)
    }
}