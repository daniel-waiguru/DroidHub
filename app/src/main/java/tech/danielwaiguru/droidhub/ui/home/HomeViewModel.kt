package tech.danielwaiguru.droidhub.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.toObject
import tech.danielwaiguru.droidhub.model.FileUpload
import tech.danielwaiguru.droidhub.model.ResultWrapper
import tech.danielwaiguru.droidhub.repository.MainRepository
import tech.danielwaiguru.droidhub.repository.MainRepositoryImpl

class HomeViewModel : ViewModel() {
    private val repository: MainRepository = MainRepositoryImpl()
    private val _files: MutableLiveData<List<FileUpload>> = MutableLiveData()
    val files: LiveData<List<FileUpload>> get() = _files
    private val _loading: MutableLiveData<Boolean> = MutableLiveData(false)
    val loading: LiveData<Boolean> get() = _loading
    fun getFiles() {
        _loading.value = true
        val query = repository.getFiles()
        query.addSnapshotListener { value, error ->
            if (error != null){
                return@addSnapshotListener
            }
            value?.let { querySnapshot ->
                val allFiles = ArrayList<FileUpload>()
                val docs = querySnapshot.documents
                docs.forEach { doc ->
                    val file = doc.toObject<FileUpload>()
                    if (file != null) {
                        allFiles.add(file)
                    }
                }
                _files.value = allFiles
                _loading.value = false
            }
        }
    }
    fun deleteFiles(documentId: String, fileName: String) {
        when(repository.deleteFile(documentId)){
            is ResultWrapper.Success -> {
                repository.freeStorage(fileName)
                Log.d(javaClass.simpleName, "Deleted")
            }
            is ResultWrapper.Failure -> {
                Log.d(javaClass.simpleName, "Error deleting resource")
            }
        }
    }
    fun signOut() = repository.signOut()
}