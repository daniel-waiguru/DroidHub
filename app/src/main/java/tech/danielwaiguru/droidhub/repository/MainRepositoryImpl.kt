package tech.danielwaiguru.droidhub.repository

import android.net.Uri
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import tech.danielwaiguru.droidhub.common.Constants.USERS_COLLECTION
import tech.danielwaiguru.droidhub.model.ResultWrapper
import tech.danielwaiguru.droidhub.model.User

class MainRepositoryImpl: MainRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
    private val database: FirebaseFirestore = FirebaseFirestore.getInstance()
    override suspend fun signUp(user: User, password: String): ResultWrapper<AuthResult> =
            try {
                val taskResult = auth.createUserWithEmailAndPassword(user.email, password).await()
                createUser(user)
                ResultWrapper.Success(taskResult)
            }
            catch (e: FirebaseAuthException){
                ResultWrapper.Failure(e.message.toString())
            }

    override suspend fun signIn(email: String, password: String) : ResultWrapper<AuthResult> =
            try {
                ResultWrapper.Success(auth.signInWithEmailAndPassword(email, password).await())
            }
            catch (e: Exception) {
                ResultWrapper.Failure(e.message.toString())
            }

    override fun createUser(user: User) {
        val userCollection = database.collection(USERS_COLLECTION)
        userCollection.add(user)
    }

    override fun saveFile(downloadUrl: String) {
        TODO("Not yet implemented")
    }

    override fun uploadFile(file: Uri) {
        TODO("Not yet implemented")
    }
}