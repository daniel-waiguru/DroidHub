package tech.danielwaiguru.droidhub.ui.upload

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tech.danielwaiguru.droidhub.R

class UploadFileFragment : Fragment() {

    companion object {
        fun newInstance() = UploadFileFragment()
    }

    private lateinit var viewModel: UploadFileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_upload_file, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UploadFileViewModel::class.java)
        // TODO: Use the ViewModel
    }

}