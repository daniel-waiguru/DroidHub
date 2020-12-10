package tech.danielwaiguru.droidhub.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import tech.danielwaiguru.droidhub.databinding.FileItemBinding
import tech.danielwaiguru.droidhub.model.FileUpload

class FileUploadViewHolder(private val binding: FileItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(file: FileUpload){
        with(binding){
            fileName.text = file.fileName
        }
    }
}