package com.example.artbookhilt.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.artbookhilt.R
import com.example.artbookhilt.adapter.ImageRecyclerAdapter
import com.example.artbookhilt.databinding.FragmentImageApiBinding
import com.example.artbookhilt.util.Status
import com.example.artbookhilt.viewmodel.ArtViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageApiFragment @Inject constructor(val imageRecyclerAdapter: ImageRecyclerAdapter) :
    Fragment(R.layout.fragment_image_api) {

    private lateinit var binding: FragmentImageApiBinding
    lateinit var viewModel: ArtViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageApiBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        imageRecyclerAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setSelectedImage(it)
        }

        binding.imageRecyclerView.adapter = imageRecyclerAdapter
        binding.imageRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)

        var job: Job? = null

        binding.searchText.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(1000)
                it?.let {
                    if (it.toString().isNotEmpty()) {
                        viewModel.searchForImage(it.toString())
                    }
                }
            }
        }

        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        viewModel.imageList.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    val urls = it.data?.hits?.map { imageResult -> imageResult.previewURL }

                    imageRecyclerAdapter.images = urls ?: listOf()

                    binding.progressBar.visibility = View.GONE
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message ?: "Error!", Toast.LENGTH_LONG)
                        .show()
                    binding.progressBar.visibility = View.GONE
                }

                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })
    }

}