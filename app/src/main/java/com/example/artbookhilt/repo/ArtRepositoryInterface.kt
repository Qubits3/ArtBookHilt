package com.example.artbookhilt.repo

import androidx.lifecycle.LiveData
import com.example.artbookhilt.model.ImageResponse
import com.example.artbookhilt.roomdb.Art
import com.example.artbookhilt.util.Resource

interface ArtRepositoryInterface {

    suspend fun insertArt(art: Art)

    suspend fun deleteArt(art: Art)

    fun getArt(): LiveData<List<Art>>

    suspend fun searchImage(imageString: String): Resource<ImageResponse>

}