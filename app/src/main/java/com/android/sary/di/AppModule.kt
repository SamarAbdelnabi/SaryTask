package com.android.sary.di

import com.android.sary.data.interceptor.HeaderInterceptor
import com.android.sary.network.AppApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [CoreDataModule::class])
@InstallIn(SingletonComponent::class)
object AppModule {

    const val READ_TIMEOUT = 30L

    @Singleton
    @Provides
    @JvmStatic
    fun provideAppApiService(
        @API retrofit: Retrofit
    ) = retrofit.create(
        AppApiService::class.java
    )

    @Provides
    @JvmStatic
    @Singleton
    @API
    fun providePrivateRetrofit(
        @API okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppApiService.BASE_URL)
            .client(okhttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    @API
    @Provides
    @JvmStatic
    fun providePrivateOkHttpClient(
        upstreamClient: OkHttpClient,
        headerInterceptor: HeaderInterceptor
    ): OkHttpClient {
        val builder = upstreamClient.newBuilder()
            .addInterceptor(headerInterceptor)

        return builder.build()
    }
}