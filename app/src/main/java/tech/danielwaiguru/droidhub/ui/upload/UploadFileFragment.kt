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
import tech.danielwaiguru.droidhub.R
import tech.danielwaiguru.droidhub.common.Constants.FILE_REQUEST_CODE
import tech.danielwaiguru.droidhub.common.gone
import tech.danielwaiguru.droidhub.common.toast
import tech.danielwaiguru.droidhub.common.visible
import tech.danielwaiguru.droidhub.databinding.FragmentUploadFileBinding
import tech.danielwaiguru.droidhub.repository.MainRepositoryImpl
import tech.danielwaiguru.droidhub.ui.viewmodel.MainViewModelFactory

class UploadFileFragment : Fragment() {
    private var _binding: FragmentUploadFileBinding? = null
    private val binding get() = _binding!!
    private val uploadFileViewModel: UploadFileViewModel by viewModels {
        MainViewModelFactory(MainRepositoryImpl())
    }
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
        subscribers()
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
    private fun subscribers() {
        uploadFileViewModel.loading.observe(viewLifecycleOwner, { loading ->
            if (loading) {
                binding.uploadProgress.visible()
            }
            else {
                binding.uploadProgress.gone()
            }
        })
        uploadFileViewModel.toast.observe(viewLifecycleOwner, { message ->
            if (message != null) {
                binding.root.toast(message)
            }
        })
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
            uploadDocument(fileName)
        }
    }
    private fun uploadDocument(fileName: String) {
        fileUri?.let {
            uploadFileViewModel.saveRFile(it, fileName)
        }
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