package tech.danielwaiguru.droidhub.model

sealed class FirebaseState {
    object Success: FirebaseState()
    data class Failure(val error: Exception): FirebaseState()
    object Loading: FirebaseState()
    object Empty: FirebaseState()
}