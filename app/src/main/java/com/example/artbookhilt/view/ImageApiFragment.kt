package com.example.artbookhilt.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.RequestManager
import com.example.artbookhilt.R
import com.example.artbookhilt.adapter.ImageRecyclerAdapter
import com.example.artbookhilt.databinding.FragmentImageApiBinding
import javax.inject.Inject

class ImageApiFragment @Inject constructor(private val imageRecyclerAdapter: ImageRecyclerAdapter) :
    Fragment(R.layout.fragment_image_api) {

    private lateinit var binding: FragmentImageApiBinding

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


    }

}