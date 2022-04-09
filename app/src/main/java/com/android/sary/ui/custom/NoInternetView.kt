package com.android.sary.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.sary.R
import com.android.sary.common.utils.onClick
import com.android.sary.common.utils.show

class NoInternetView(context: Context, attrs: AttributeSet? = null) : ConstraintLayout(context, attrs) {


    private val errorTitle: TextView
    private val errorBody : TextView
    val reloadMapBtn : TextView

    var loadingTask : () -> Unit = {}

    init {
        inflate(context, R.layout.custom_no_internet_view, this)

        errorTitle = findViewById(R.id.errorTitle)
        errorBody = findViewById(R.id.errorBody)
        reloadMapBtn = findViewById(R.id.reloadMapBtn)

        errorTitle.text = context.getString(R.string.error_handler_no_internet_connection)
        reloadMapBtn.text =  context.getString(R.string.common_reload)

        reloadMapBtn.onClick {
            this.loadingTask()
        }
    }

    fun setup(loadingTask : () -> Unit) {
        this.loadingTask = loadingTask
    }

    fun setErrorBody(error : String) {
        errorBody.show()
        errorBody.text = error
    }

}