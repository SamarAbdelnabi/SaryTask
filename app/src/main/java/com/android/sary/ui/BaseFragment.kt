package com.android.sary.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.android.sary.R
import com.android.sary.common.ui.SingleLiveEvent
import com.android.sary.common.utils.DialogType
import com.android.sary.common.utils.onClick
import com.android.sary.common.utils.showTopSnackBar
import com.android.sary.ui.custom.NoInternetView

abstract class BaseFragment : Fragment() {

    abstract val viewModel: BaseViewModel
    var internetView: ViewGroup? = null
    var _binding: ViewBinding? = null

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? BaseActivity)?.setStatusBarColor()
    }

    fun attachNoInternetView(
        loadingTask: () -> Unit,
        errorBody: String? = null
    ) {
        val viewGroup = _binding?.root as? ViewGroup?

        viewGroup?.let {
            internetView = NoInternetView(viewGroup.context)

            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )

            (internetView as NoInternetView).layoutParams = lp
            internetView?.visibility = View.GONE
            viewGroup.addView(internetView)

            (internetView as NoInternetView).reloadMapBtn.onClick {
                loadingTask.invoke()
            }

        }
    }

    fun showGeneralErrorTopSnackBar(){
        _binding?.root?.let {
            val msg = context?.getString(R.string.error_handler_something_went_wrong)
            showTopSnackBar(it, msg.orEmpty(), type = DialogType.ERROR)
        }
    }
}

abstract class BaseViewModel() : ViewModel() {
    companion object AppBaseLiveData {
    }

    var failure = SingleLiveEvent<SharedUiState>()

    /**
     * For showing Sucess, Error Message event (Snackbar),
     * The fragment which extends **[BaseViewModelFragment]** must call super.onViewCreated(view, savedInstanceState)
     * to Observe theses LiveDataEvents.
     */
    val showSnackbarErrorMsg = SingleLiveEvent<String>()
    val showSnackbarSuccessMsg = SingleLiveEvent<String>()

//    fun <T> handleFailure(result: Result<T>): Boolean {
//        if (result.status == Result.Status.ERROR && result.error?.code == 503) {
//            handleServerBusyState()
//            return true
//        }
//        return false
//    }

    fun handleServerBusyState(){
        failure.postValue(SharedUiState.ServerBusyState)
    }
    sealed class SharedUiState {
        object ServerBusyState : SharedUiState()
    }
}