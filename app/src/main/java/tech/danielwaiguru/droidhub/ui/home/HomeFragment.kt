package tech.danielwaiguru.droidhub.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import tech.danielwaiguru.droidhub.R
import tech.danielwaiguru.droidhub.common.gone
import tech.danielwaiguru.droidhub.common.visible
import tech.danielwaiguru.droidhub.databinding.FragmentHomeBinding
import tech.danielwaiguru.droidhub.model.FileUpload
import tech.danielwaiguru.droidhub.repository.MainRepositoryImpl
import tech.danielwaiguru.droidhub.ui.adapter.FileUploadAdapter

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val fileAdapter: FileUploadAdapter by lazy { FileUploadAdapter() }
    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(MainRepositoryImpl())
    }
    private val files = ArrayList<FileUpload>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        setupRecyclerView()
        homeViewModel.getFiles()
        subscribers()
    }
    private fun subscribers() {
        homeViewModel.loading.observe(viewLifecycleOwner,  { loading ->
            if (loading){
                binding.filesProgress.visible()
            }
            else {
                binding.filesProgress.gone()
            }
        })
        homeViewModel.files.observe(viewLifecycleOwner, {
            files.clear()
            files.addAll(it)
            fileAdapter.submitList(files)
        })
    }
    private fun initListeners() {
        with(binding) {
            addFile.setOnClickListener { startUploadUi() }
            actionLogout.setOnClickListener { signOut() }
        }
    }
    private fun signOut() {
        findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToSignInFragment()
        )
        homeViewModel.signOut()
    }
    private fun setupRecyclerView() = binding.filesRecyclerView.apply {
        layoutManager = LinearLayoutManager(requireContext())
        setHasFixedSize(true)
        adapter = fileAdapter
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(this)
    }
    private val itemTouchHelper = object: ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val itemSwiped = files[position]
            homeViewModel.deleteFiles(itemSwiped.id, itemSwiped.fileName)
            fileAdapter.notifyItemRemoved(position)
            Snackbar.make(requireView(), getString(R.string.delete_item), Snackbar.LENGTH_LONG)
        }
    }
    private fun startUploadUi() {
        val action = HomeFragmentDirections.actionHomeFragmentToUploadFileFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}