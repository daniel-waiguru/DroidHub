package tech.danielwaiguru.droidhub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import tech.danielwaiguru.droidhub.databinding.FileItemBinding
import tech.danielwaiguru.droidhub.model.FileUpload

class FileUploadAdapter(private val listener: OnFileItemClickListener):
        RecyclerView.Adapter<FileUploadViewHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<FileUpload>() {
        override fun areItemsTheSame(oldItem: FileUpload, newItem: FileUpload): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FileUpload, newItem: FileUpload): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)
    fun submitList(files: List<FileUpload>) = differ.submitList(files)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileUploadViewHolder {
        return FileUploadViewHolder(
                FileItemBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                )
        )
    }

    override fun onBindViewHolder(holder: FileUploadViewHolder, position: Int) {
        holder.bind(differ.currentList[position], listener)
    }

    override fun getItemCount(): Int = differ.currentList.size
    interface OnFileItemClickListener {
        fun onFileItemClicked(fileUpload: FileUpload)
    }
}