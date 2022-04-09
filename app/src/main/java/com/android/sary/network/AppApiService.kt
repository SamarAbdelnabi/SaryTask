package com.android.sary.network

import com.android.sary.data.entity.BannersResponse
import com.android.sary.data.entity.Catalog
import retrofit2.Response
import retrofit2.http.GET

interface AppApiService {

    companion object {
        var BASE_URL = "https://staging.sary.to/"
    }

    @GET("api/v2.5.1/baskets/325514/banners")
    suspend fun getBanners():Response<BannersResponse>

    @GET("api/baskets/325514/catalog")
    suspend fun getCatalog():Response<Catalog>

}