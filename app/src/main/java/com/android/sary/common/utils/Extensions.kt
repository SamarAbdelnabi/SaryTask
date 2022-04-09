package com.android.sary.common.utils

import android.content.Context
import android.content.res.ColorStateList
import android.net.ConnectivityManager
import android.os.SystemClock
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.android.sary.R
import com.android.sary.ui.BaseActivity
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

fun View.onClick(body: (View) -> Unit) = setOnClickListener(SingleClickListener { body(it) })

fun Fragment.setTitle(title: String) {
    (activity as AppCompatActivity).supportActionBar!!.title = title
}

fun ProgressBar.hide() = apply {
    visibility = View.GONE
}

fun ProgressBar.show() = apply {
    visibility = View.VISIBLE
}

fun View.hide() = apply {
    visibility = View.GONE
}

fun View.invisible() = apply {
    visibility = View.INVISIBLE
}

fun View.show() = apply {
    visibility = View.VISIBLE
}

/**
 * This type of [View.OnClickListener] prevents multiple view clicks at one time.
 */
class SingleClickListener(
    private var defaultInterval: Int = 1000,
    private val onSingleClick: (View) -> Unit
) : View.OnClickListener {
    private var lastTimeClicked: Long = 0
    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        onSingleClick(v)
    }
}
enum class DialogType(
    @DrawableRes val icon: Int,
    @ColorRes val iconBackgroundTint: Int
) {
    WARN(
        R.drawable.ic_close_white_24dp,
        R.color.dialog_warn_type
    ),
    ERROR(
        R.drawable.ic_close_white_24dp,
        R.color.dialog_error_type
    ),
    CONFIRMATION(
        R.drawable.ic_close_white_24dp,
        R.color.dialog_confirmation_type
    )
}

fun showToast(context: Context, @StringRes msg: Int) {
    Toast.makeText(context, context.getString(msg), Toast.LENGTH_SHORT).show()
}

fun showToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

fun showLongToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
}

fun showTopSnackBar(
    view: View?,
    msg: String,
    length: Int = Snackbar.LENGTH_LONG,
    actionText: String? = null,
    isBottomSheet: Boolean = false,
    action: ((View) -> Unit)? = {},
    type: DialogType = DialogType.CONFIRMATION,
    maxLines:Int = 4

) {
    if (view != null) {
        val snackBarView = Snackbar.make(view, msg, length)
        try {
            if (actionText != null && action != null)
                snackBarView.setAction(actionText) {
                    snackBarView.dismiss()
                    action?.invoke(it)
                }
            val view = snackBarView.view


            //try {
            if (isBottomSheet) {
                val frameLayoutParams = view.layoutParams as FrameLayout.LayoutParams
                frameLayoutParams.gravity = Gravity.TOP
                val height =frameLayoutParams.height+R.dimen.margin4
                frameLayoutParams.topMargin =
                    view.context.resources.getDimension(height).toInt()
                view.layoutParams = frameLayoutParams
            } else {
                val coordinatorLayoutParams = view.layoutParams as CoordinatorLayout.LayoutParams
                coordinatorLayoutParams.gravity = Gravity.TOP
                view.layoutParams = coordinatorLayoutParams
            }
            if (type != null) {
                view.backgroundTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            view.context,
                            type.iconBackgroundTint
                        )
                    )
            }

            val textView: TextView =
                snackBarView.view.findViewById(com.google.android.material.R.id.snackbar_text)
            textView.maxLines = maxLines

            snackBarView.setTextColor(R.color.always_white.getColor(view.context))
        } catch (e: Exception) {
            e.printStackTrace()

        }
        snackBarView.show()
    }
}

fun Int.getColor(context: Context): Int {
    return context.resources.getColor(this, null);
}

fun Context.isConnectedOrConnecting(): Boolean {
    val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connMgr.activeNetwork
    if (networkInfo.isNotNull()) {
        connMgr.getNetworkCapabilities(networkInfo) ?: return false
    }
    return networkInfo.isNotNull()
}

fun Any?.isNull() = this == null

fun Any?.isNotNull() = this != null

fun Any?.toGson(): String = Gson().toJson(this)

fun View.hideIf(body: () -> Boolean) = apply {
    visibility = if (body()) View.GONE else View.VISIBLE
}

fun View.showIf(body: () -> Boolean?) = apply {
    visibility = if (body() == true) View.VISIBLE else View.GONE
}