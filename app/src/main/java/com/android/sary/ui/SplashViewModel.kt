package com.android.sary.ui

import androidx.lifecycle.viewModelScope
import com.android.sary.common.ui.SingleLiveEvent
import com.android.sary.common.ui.state.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor() : BaseViewModel() {
    var loadingLiveData = SingleLiveEvent<ViewState<Unit>>()
    fun delay() {
        loadingLiveData.postValue(ViewState.Loading())
        viewModelScope.launch {
            kotlinx.coroutines.delay(2000)
            loadingLiveData.postValue(ViewState.Success(Unit))

        }
    }

}