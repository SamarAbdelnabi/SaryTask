package com.android.sary.di

import com.android.sary.di.AppModule.READ_TIMEOUT
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreDataModule {

    @Provides
    @Singleton
    @JvmStatic
    fun provideOkHttpClient(
    ): OkHttpClient {
        val httpclient = OkHttpClient.Builder()
            .connectTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        return httpclient.build()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    @JvmStatic
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

}