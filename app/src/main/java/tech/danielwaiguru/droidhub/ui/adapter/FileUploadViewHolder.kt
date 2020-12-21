package tech.danielwaiguru.droidhub.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import tech.danielwaiguru.droidhub.databinding.FileItemBinding
import tech.danielwaiguru.droidhub.model.FileUpload

class FileUploadViewHolder(private val binding: FileItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(file: FileUpload, listener: FileUploadAdapter.OnFileItemClickListener){
        with(binding){
            fileName.text = file.fileName
            binding.root.setOnClickListener { listener.onFileItemClicked(file) }
        }
    }
}