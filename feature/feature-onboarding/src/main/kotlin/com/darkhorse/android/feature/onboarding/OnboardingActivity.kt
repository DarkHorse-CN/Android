package com.darkhorse.android.feature.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.darkhorse.android.feature.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    OnboardingScreen(
                        onCompleted = {
                            markOnboardingCompleted(this)
                            startActivity(Intent(this, HomeActivity::class.java))
                            finish()
                        },
                    )
                }
            }
        }
    }

    companion object {
        private const val PREFS_NAME = "onboarding_prefs"
        private const val KEY_COMPLETED = "onboarding_completed"

        fun isFirstInstall(context: Context): Boolean {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            return !prefs.getBoolean(KEY_COMPLETED, false)
        }

        fun markOnboardingCompleted(context: Context) {
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(KEY_COMPLETED, true)
                .apply()
        }
    }
}
