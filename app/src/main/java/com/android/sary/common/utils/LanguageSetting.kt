package com.android.sary.common.utils

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.LocaleList
import android.preference.PreferenceManager
import com.android.sary.local.SecurePreferences
import java.util.*
import javax.inject.Inject

object LanguageSetting {
    private const val CURRENT_LANGUAGE = "pref_current_language"

    fun wrapContext(context: Context): Context {
        var currentLocale = getLocale(context)

        Locale.setDefault(currentLocale)
        val newConfig = Configuration()
        newConfig.setLocale(currentLocale)

        val newContext = context.createConfigurationContext(newConfig)

        return adjustFontScale(newContext, newContext.resources.configuration)
    }

    fun getLocale(context: Context): Locale {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        val currentLang = sharedPreferences.getString(CURRENT_LANGUAGE, "ar") ?: "ar"
        return if (currentLang.isNullOrBlank()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                Resources.getSystem().configuration.locales[0]
            else
                Resources.getSystem().configuration.locale
        } else {
            Locale(currentLang)
        }
    }

    private fun adjustFontScale(context: Context, configuration: Configuration): Context {
        if (configuration.fontScale > 1.0) {
            configuration.fontScale = 1.0.toFloat()
            return context.createConfigurationContext(configuration)
        }
        return context
    }

    @Suppress("DEPRECATION")
    fun getResources(activity: Context, resources: Resources): Resources {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val locale = getLocale(activity)
            val config = resources.configuration
            config.setLocale(locale)
            val localeList = LocaleList(locale)
            LocaleList.setDefault(localeList)
            config.setLocales(localeList)
            resources
        } else {
            val config = resources.configuration
            config.locale = getLocale(activity)
            config.setLayoutDirection(config.locale) // fixes language direction in Api 23
            val metrics = resources.displayMetrics
            Resources(activity.assets, metrics, config)
        }
    }

    fun setLanguage(context: Context, language: String) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences.edit().putString(CURRENT_LANGUAGE, language).commit()
    }
}