package tech.danielwaiguru.droidhub.ui.upload

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tech.danielwaiguru.droidhub.model.ResultWrapper
import tech.danielwaiguru.droidhub.repository.MainRepository

class UploadFileViewModel(private val repository: MainRepository ) : ViewModel() {
    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean>
        get() = _loading
    private val _toast: MutableLiveData<String> = MutableLiveData()
    val toast: LiveData<String?>
        get() = _toast
    fun saveRFile(fileUri: Uri, fileName: String) {
        _loading.value = true
        viewModelScope.launch {
            val downloadUrl = getDownloadUri(fileUri, fileName)
            when (repository.saveFile(fileName, downloadUrl)){
                is ResultWrapper.Success -> {
                    _loading.postValue(false)
                    _toast.postValue("File Uploaded successfully")
                }
                is ResultWrapper.Failure -> {
                    _loading.postValue(false)
                    _toast.postValue(
                            "Error encountered while uploading your resource, Please try again"
                    )
                }
            }
        }
    }
    private suspend fun getDownloadUri(fileUri: Uri, fileName: String): String{
        var downloadUrl: String? = null
        downloadUrl = when (val result = repository.uploadFile(fileUri, fileName)){
            is ResultWrapper.Success -> {
                result.data
            }
            is  ResultWrapper.Failure -> {
                ""
            }
        }
        return downloadUrl ?: ""
    }
}