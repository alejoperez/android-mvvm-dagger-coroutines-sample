package com.mvvm.dagger.coroutines.sample.splash

import android.os.Bundle
import android.os.Handler
import androidx.annotation.VisibleForTesting
import com.mvvm.dagger.coroutines.sample.R
import com.mvvm.dagger.coroutines.sample.base.BaseActivity
import com.mvvm.dagger.coroutines.sample.databinding.ActivitySplashBinding
import com.mvvm.dagger.coroutines.sample.main.MainActivity
import com.mvvm.dagger.coroutines.sample.register.RegisterActivity
import org.jetbrains.anko.startActivity

class SplashActivity : BaseActivity<SplashViewModel, ActivitySplashBinding>() {

    companion object {
        const val SPLASH_DELAY = 2000L
    }

    override fun getLayoutId(): Int = R.layout.activity_splash
    override fun getViewModelClass(): Class<SplashViewModel> = SplashViewModel::class.java
    override fun getVariablesToBind(): Map<Int, Any> = emptyMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler().postDelayed({ goToNextScreen(viewModel.isUserLoggedIn()) }, SPLASH_DELAY)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun goToNextScreen(isLoggedIn: Boolean) {
        if (isLoggedIn) {
            startActivity<MainActivity>()
        } else {
            startActivity<RegisterActivity>()
        }
        finish()
    }

}
