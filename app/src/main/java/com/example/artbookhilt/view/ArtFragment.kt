package com.example.artbookhilt.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.artbookhilt.R
import com.example.artbookhilt.adapter.ArtRecyclerAdapter
import com.example.artbookhilt.databinding.FragmentArtsBinding
import com.example.artbookhilt.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtFragment @Inject constructor(
    private val artRecyclerAdapter: ArtRecyclerAdapter
) : Fragment(R.layout.fragment_arts) {

    private lateinit var binding: FragmentArtsBinding
    lateinit var viewModel: ArtViewModel

    private val swipeCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val layoutPosition = viewHolder.layoutPosition
                val selectedArt = artRecyclerAdapter.arts[layoutPosition]
                viewModel.deleteArt(selectedArt)
            }

        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArtsBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        subscribeToObservers()

        binding.recyclerViewArt.adapter = artRecyclerAdapter
        binding.recyclerViewArt.layoutManager = LinearLayoutManager(requireContext())
        ItemTouchHelper(swipeCallback).attachToRecyclerView(binding.recyclerViewArt)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            findNavController().navigate(ArtFragmentDirections.actionArtFragmentToArtDetailsFragment())
        }
    }

    private fun subscribeToObservers() {
        viewModel.artList.observe(viewLifecycleOwner, {
            artRecyclerAdapter.arts = it
        })
    }

}