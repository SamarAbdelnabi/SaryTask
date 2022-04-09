package com.android.sary.ui.storeFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.sary.common.ui.SingleLiveEvent
import com.android.sary.common.ui.state.ViewState
import com.android.sary.common.utils.isNotNull
import com.android.sary.data.entity.Catalog
import com.android.sary.data.entity.CatalogResult
import com.android.sary.data.entity.ResultResponse
import com.android.sary.domain.GetBannersUseCase
import com.android.sary.domain.GetCatalogUseCase
import com.android.sary.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class StoreViewModel  @Inject constructor(
    private val getBannersUseCase: GetBannersUseCase,
    private val getCatalogUseCase: GetCatalogUseCase,
) : BaseViewModel() {

    var bannersListLiveData  = MutableLiveData<ViewState<List<ResultResponse>>>()
    var catalogListLiveData = MutableLiveData<ViewState<Catalog>>()

    fun getBanners(){
        bannersListLiveData.postValue(ViewState.Loading())
        viewModelScope.launch {
            var result = getBannersUseCase()
            Timber.d("Result = ${result}")
            if(result.isSuccessful() && result.data.isNotNull()){
                if(result.data?.result.isNullOrEmpty())
                    bannersListLiveData.postValue(ViewState.Empty())
                else
                    bannersListLiveData.postValue(ViewState.Success(result.data?.result?: emptyList()))
            }
        }
    }

    fun getCatalog(){
        catalogListLiveData.postValue(ViewState.Loading())
        viewModelScope.launch {
            var result = getCatalogUseCase()
            Timber.d("Result = ${result}")
            if(result.isSuccessful() && result.data.isNotNull()){
                if(result.data?.result.isNullOrEmpty())
                    catalogListLiveData.postValue(ViewState.Empty())
                else
                    catalogListLiveData.postValue(ViewState.Success(result.data!!))
            }
        }
    }
}