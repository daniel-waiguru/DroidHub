package tech.danielwaiguru.droidhub.model

sealed class ResultWrapper<T>(
        val data: T? = null,
        val message: String? = null
) {
    class Success<T>(data: T) : ResultWrapper<T>(data)
    class Failure<T>(message: String, data: T? = null) : ResultWrapper<T>(data, message)
}