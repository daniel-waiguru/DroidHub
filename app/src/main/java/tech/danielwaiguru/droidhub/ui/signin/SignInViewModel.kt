package tech.danielwaiguru.droidhub.ui.signin

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthResult
import tech.danielwaiguru.droidhub.model.ResultWrapper
import tech.danielwaiguru.droidhub.repository.MainRepository
import tech.danielwaiguru.droidhub.repository.MainRepositoryImpl

class SignInViewModel : ViewModel() {
    private val repository: MainRepository = MainRepositoryImpl()
    suspend fun userSignIn(email: String, password: String): ResultWrapper<AuthResult> {
        return repository.signIn(email, password)
    }
}