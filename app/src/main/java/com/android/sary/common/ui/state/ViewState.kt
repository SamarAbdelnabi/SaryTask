package com.android.sary.common.ui.state

sealed class ViewState<T> {
    class Success<T>(val data: T) : ViewState<T>()
    class Loading<T> : ViewState<T>()
    class Empty<T>(
        val title: String? = "",
        val message: String? = "",
        val iconRes: Int = -1
    ) : ViewState<T>()

    class Error<T>(val code: Int? = 0, val title: String? = "", val message: String? = "") :
        ViewState<T>()
    class BusinessError<T>(val code: Int? = 0, val title: String? = "", val message: String? = "") :
        ViewState<T>()
    class NoInternetView<T> : ViewState<T>()
}