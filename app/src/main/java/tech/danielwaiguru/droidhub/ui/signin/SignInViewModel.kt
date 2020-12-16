package tech.danielwaiguru.droidhub.ui.signin

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthResult
import tech.danielwaiguru.droidhub.model.ResultWrapper
import tech.danielwaiguru.droidhub.repository.MainRepository

class SignInViewModel(private val repository: MainRepository) : ViewModel() {
    suspend fun userSignIn(email: String, password: String): ResultWrapper<AuthResult> {
        return repository.signIn(email, password)
    }
}