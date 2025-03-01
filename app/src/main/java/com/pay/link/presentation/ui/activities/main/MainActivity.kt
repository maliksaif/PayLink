package com.pay.link.presentation.ui.activities.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.pay.link.R
import com.pay.link.databinding.ActivityMainBinding
import com.pay.link.presentation.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
        navController = navHostFragment?.navController ?: throw IllegalStateException("NavController not found")

        observeUserAuthState()

    }

    private fun observeUserAuthState() {
        viewModel.isUserLoggedInLiveData.observe(this) { isLoggedIn ->
            Handler(Looper.getMainLooper()).post {
                val currentDest = navController.currentDestination?.id

                if (isLoggedIn && currentDest != R.id.home_fragment) {
                    navController.navigate(
                        R.id.home_fragment,
                        null,
                        NavOptions.Builder()
                            .setLaunchSingleTop(true)
                            .setPopUpTo(R.id.nav_graph, inclusive = true)
                            .build()
                    )
                } else if (!isLoggedIn && currentDest != R.id.login_fragment) {
                    navController.navigate(
                        R.id.login_fragment,
                        null,
                        NavOptions.Builder()
                            .setLaunchSingleTop(true)
                            .setPopUpTo(R.id.nav_graph, inclusive = true)
                            .build()
                    )
                }
            }
        }
    }
}
