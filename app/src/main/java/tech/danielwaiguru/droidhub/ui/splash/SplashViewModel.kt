package tech.danielwaiguru.droidhub.ui.splash

import androidx.lifecycle.ViewModel
import tech.danielwaiguru.droidhub.repository.MainRepository

class SplashViewModel(private val repository: MainRepository): ViewModel() {
    fun getCurrentUser() = repository.getCurrentUser()
}