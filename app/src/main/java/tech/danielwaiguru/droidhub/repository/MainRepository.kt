package tech.danielwaiguru.droidhub.repository

import android.net.Uri
import com.google.firebase.auth.AuthResult
import tech.danielwaiguru.droidhub.model.ResultWrapper
import tech.danielwaiguru.droidhub.model.User

interface MainRepository {
    suspend fun signUp(user: User, password: String): ResultWrapper<AuthResult>
    fun signIn(email: String, password: String)
    fun createUser(user: User)
    fun saveFile(downloadUrl: String)
    fun uploadFile(file: Uri)
}