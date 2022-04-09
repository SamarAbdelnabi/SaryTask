package com.android.sary.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.android.sary.common.ui.state.ViewState
import com.android.sary.common.utils.*
import com.android.sary.databinding.ActivitySplashBinding
import com.android.sary.local.SecurePreferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    @Inject
    lateinit var securePreferences: SecurePreferences

    private val viewModel by viewModels<SplashViewModel>()
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        LanguageSetting.setLanguage(this, CURRENT_LANGUAGE)
        securePreferences.setAccessToken(TOKEN)

        viewModel.loadingLiveData.observe(this, Observer {
            when (it) {
                is ViewState.Loading -> {
                    binding.progressBar.show()
                }
                is ViewState.Success -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    binding.progressBar.hide()
                    finish()

                }
                is ViewState.Empty -> {
                    binding.progressBar.hide()
                }
                is ViewState.Error -> {
                    binding.progressBar.hide()
                }
            }
        })

        viewModel.delay()
    }

}