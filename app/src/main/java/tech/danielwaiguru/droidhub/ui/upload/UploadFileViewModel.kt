package tech.danielwaiguru.droidhub.ui.upload

import android.net.Uri
import androidx.lifecycle.ViewModel
import tech.danielwaiguru.droidhub.model.ResultWrapper
import tech.danielwaiguru.droidhub.repository.MainRepository
import tech.danielwaiguru.droidhub.repository.MainRepositoryImpl

class UploadFileViewModel : ViewModel() {
    private val repository: MainRepository = MainRepositoryImpl()
    fun saveFile(fileName: String, downloadUrl: String) {
        repository.saveFile(fileName, downloadUrl)
    }
    suspend fun uploadFile(fileUri: Uri, fileName: String): ResultWrapper<String> {
        return repository.uploadFile(fileUri, fileName)
    }
}