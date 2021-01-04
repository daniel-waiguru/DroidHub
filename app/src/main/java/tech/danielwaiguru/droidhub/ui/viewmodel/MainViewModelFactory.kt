package tech.danielwaiguru.droidhub.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tech.danielwaiguru.droidhub.repository.MainRepository
import tech.danielwaiguru.droidhub.ui.home.HomeViewModel
import tech.danielwaiguru.droidhub.ui.signin.SignInViewModel
import tech.danielwaiguru.droidhub.ui.signup.SignUpViewModel
import tech.danielwaiguru.droidhub.ui.splash.SplashViewModel
import tech.danielwaiguru.droidhub.ui.upload.UploadFileViewModel

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val repository: MainRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SignInViewModel::class.java) -> {
                SignInViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> {
                SignUpViewModel(repository) as T
            }
            modelClass.isAssignableFrom(UploadFileViewModel::class.java) -> {
                UploadFileViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                SplashViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown exception class")
        }
    }
}