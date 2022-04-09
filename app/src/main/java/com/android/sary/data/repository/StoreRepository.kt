package com.android.sary.data.repository

import com.android.sary.data.entity.BannersResponse
import com.android.sary.data.entity.Catalog
import com.android.sary.network.AppApiService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoreRepository @Inject constructor(
    private val appApiService: AppApiService
) {
    suspend fun getBanners(): Response<BannersResponse> {
        return appApiService.getBanners()
    }

    suspend fun getCatalog():Response<Catalog>{
        return appApiService.getCatalog()
    }
}