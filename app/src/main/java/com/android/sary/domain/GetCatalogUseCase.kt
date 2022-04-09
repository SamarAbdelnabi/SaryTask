package com.android.sary.domain

import com.android.sary.data.entity.Catalog
import com.android.sary.data.repository.StoreRepository
import com.android.sary.network.Result
import dagger.Reusable
import javax.inject.Inject

@Reusable
class GetCatalogUseCase @Inject constructor(val storeRepository: StoreRepository):BaseUseCase() {
    suspend operator fun invoke(): Result<Catalog> {
        return getResult { storeRepository.getCatalog() }
    }
}