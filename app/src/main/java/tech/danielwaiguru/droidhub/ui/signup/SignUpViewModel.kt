package tech.danielwaiguru.droidhub.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.launch
import tech.danielwaiguru.droidhub.model.ResultWrapper
import tech.danielwaiguru.droidhub.model.User
import tech.danielwaiguru.droidhub.repository.MainRepository
import tech.danielwaiguru.droidhub.repository.MainRepositoryImpl

class SignUpViewModel : ViewModel() {
    private val repository: MainRepository = MainRepositoryImpl()
    private val _message: MutableLiveData<String> = MutableLiveData()
    val message: LiveData<String> get() = _message
    private val _loading: MutableLiveData<Boolean> = MutableLiveData(false)
    val loading: LiveData<Boolean> get() = _loading
    fun signUp(user: User, password: String) {
        _loading.value = true
        viewModelScope.launch {
            when(val result = repository.signUp(user, password)){
                is  ResultWrapper.Success -> {
                    repository.createUser(user)
                    _loading.postValue(false)
                    _message.postValue("User registered successfully!")
                }
                is ResultWrapper.Failure -> {
                    _loading.postValue(false)
                    _message.postValue(result.message.toString())
                }
            }
        }
    }
    suspend fun userSignUp(user: User, password: String): ResultWrapper<AuthResult> {
        return repository.signUp(user, password)
    }

}