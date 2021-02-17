package com.example.artbookhilt.repo

import androidx.lifecycle.LiveData
import com.example.artbookhilt.api.RetrofitAPI
import com.example.artbookhilt.model.ImageResponse
import com.example.artbookhilt.roomdb.Art
import com.example.artbookhilt.roomdb.ArtDAO
import com.example.artbookhilt.util.Resource
import java.lang.Exception
import javax.inject.Inject

class ArtRepository @Inject constructor(
    private val artDAO: ArtDAO,
    private val retrofitAPI: RetrofitAPI
) : ArtRepositoryInterface {

    override suspend fun insertArt(art: Art) {
        artDAO.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDAO.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDAO.observeArts()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return try {
            val response = retrofitAPI.imageSearch(imageString)

            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            } else {
                Resource.error("Error", null)
            }

        } catch (e: Exception) {
            Resource.error("No data", null)
        }
    }
}