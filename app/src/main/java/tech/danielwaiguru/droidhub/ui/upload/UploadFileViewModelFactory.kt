package tech.danielwaiguru.droidhub.ui.upload

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tech.danielwaiguru.droidhub.repository.MainRepository

class UploadFileViewModelFactory(private val repository: MainRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UploadFileViewModel::class.java)){
            return UploadFileViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View model class")
    }
}