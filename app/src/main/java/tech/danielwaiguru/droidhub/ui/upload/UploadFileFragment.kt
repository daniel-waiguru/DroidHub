package tech.danielwaiguru.droidhub.ui.upload

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import tech.danielwaiguru.droidhub.R
import tech.danielwaiguru.droidhub.common.Constants.FILE_REQUEST_CODE
import tech.danielwaiguru.droidhub.common.gone
import tech.danielwaiguru.droidhub.common.toast
import tech.danielwaiguru.droidhub.common.visible
import tech.danielwaiguru.droidhub.databinding.FragmentUploadFileBinding
import tech.danielwaiguru.droidhub.model.ResultWrapper

class UploadFileFragment : Fragment() {
    private var _binding: FragmentUploadFileBinding? = null
    private val binding get() = _binding!!
    private val uploadFileViewModel: UploadFileViewModel by viewModels()
    private var fileUri: Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUploadFileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            data?.let { uri ->
                fileUri = uri.data
            }
        }
    }
    private fun initListeners() {
        with(binding) {
            chooseFileButton.setOnClickListener { chooseFile() }
            uploadFileButton.setOnClickListener { saveFile() }
        }
    }

    private fun chooseFile() {
        initChooser()
    }

    private fun initChooser() {
        Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "application/*"
            startActivityForResult(this, FILE_REQUEST_CODE)
        }
    }

    private fun saveFile() {
        val fileName = binding.fileName.text.toString()
        if (validInput()){
            binding.uploadProgress.visible()
            val downloadUrl = uploadFile(fileName)
            uploadFileViewModel.saveFile(fileName, downloadUrl)
        }
    }
    private fun uploadFile(fileName: String): String {
        var downloadUrl: String? = null
        lifecycleScope.launch {
            when (val res = uploadFileViewModel.uploadFile(fileUri!!, fileName)) {
                is ResultWrapper.Success -> {
                    binding.uploadProgress.gone()
                    downloadUrl = res.data.toString()
                }
                is ResultWrapper.Failure -> {
                    binding.uploadProgress.gone()
                    res.message?.let { binding.root.toast(it) }
                }
            }
        }
        return downloadUrl as String
    }
    private fun validInput(): Boolean {
        with(binding){
            if (fileName.text.isEmpty()){
                fileName.error = getString(R.string.file_error)
                fileName.requestFocus()
                return false
            }
        }
        return true
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}