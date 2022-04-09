package com.android.sary.ui

import android.content.Context
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.android.sary.R
import com.android.sary.common.utils.LanguageSetting

open class BaseActivity : AppCompatActivity() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LanguageSetting.wrapContext(base))
    }
    override fun getResources(): Resources {
        return LanguageSetting.getResources(this, super.getResources())
    }

    fun setStatusBarColor(
        color: Int = R.color.windowBackground
    ) = apply { window?.statusBarColor = ContextCompat.getColor(this, color) }
}