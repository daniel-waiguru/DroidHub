package tech.danielwaiguru.droidhub.repository

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import tech.danielwaiguru.droidhub.model.ResultWrapper
import tech.danielwaiguru.droidhub.model.User

interface MainRepository {
    suspend fun signUp(user: User, password: String): ResultWrapper<AuthResult>
    suspend fun signIn(email: String, password: String): ResultWrapper<AuthResult>
    fun createUser(user: User)
    fun saveFile(fileName: String, downloadUrl: String): ResultWrapper<Task<Void>>
    suspend fun uploadFile(fileUri: Uri, fileName: String): ResultWrapper<String>
}