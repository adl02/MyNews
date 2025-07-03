package com.howtokaise.mynews.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.howtokaise.mynews.domain.navigation.Navigate
import com.howtokaise.mynews.domain.data.room.AppDatabase
import com.howtokaise.mynews.presentation.ui.theme.MyNewsTheme

class MainActivity : ComponentActivity() {

    private var backPressedTime = 0L
    private lateinit var backToast: Toast

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        backToast = Toast.makeText(this, "Press back to exit", Toast.LENGTH_SHORT)

        onBackPressedDispatcher.addCallback(this){
            val currentTime = System.currentTimeMillis()
            if (currentTime - backPressedTime < 2000){
                backToast.cancel()
                finish()
            } else {
                backToast.show()
                backPressedTime = currentTime
            }
        }
        enableEdgeToEdge()
        setContent {

            MyNewsTheme {

                val systemUiController = rememberSystemUiController()
                val isDarkTheme = isSystemInDarkTheme()

                val navController = rememberNavController()

                val dao = AppDatabase.getDatabase(applicationContext).articleDao()

                val sharedViewModel: NewViewModel = viewModel(
                    factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return NewViewModel(dao) as T
                        }
                    }
                )

                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = if(isDarkTheme) Color.Black else Color.White,
                        darkIcons = !isDarkTheme
                    )
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomBar(navController)
                    }

                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(if (isDarkTheme) Color.Black else Color.White)
                    ) {
                        Navigate(navController, sharedViewModel)
                    }
                }
            }
        }
    }
}

