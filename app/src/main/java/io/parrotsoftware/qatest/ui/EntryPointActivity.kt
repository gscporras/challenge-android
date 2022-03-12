package io.parrotsoftware.qatest.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.CompositionLocalProvider
import dagger.hilt.android.AndroidEntryPoint
import io.parrotsoftware.qatest.ui.main.MainScreen
import io.parrotsoftware.qatest.ui.main.viewmodel.MainViewModel
import io.parrotsoftware.qatest.ui.theme.ParrotSoftwareTheme

@AndroidEntryPoint
class EntryPointActivity: AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel.isLoggedIn()

        setContent {
            CompositionLocalProvider {
                ParrotSoftwareTheme {
                    MainScreen(mainViewModel)
                }
            }
        }
    }
}