package com.android.sary.domain

import com.android.sary.data.entity.BannersResponse
import com.android.sary.data.repository.StoreRepository
import com.android.sary.network.Result
import dagger.Reusable
import javax.inject.Inject

@Reusable
class GetBannersUseCase @Inject constructor(val storeRepository: StoreRepository):BaseUseCase() {
    suspend operator fun invoke(): Result<BannersResponse> {
        return getResult { storeRepository.getBanners() }
    }
}