package com.hakancevik.hiltprojectartbook.repo

import androidx.lifecycle.LiveData
import com.hakancevik.hiltprojectartbook.api.PixabayAPI
import com.hakancevik.hiltprojectartbook.model.Art
import com.hakancevik.hiltprojectartbook.model.ImageResponse
import com.hakancevik.hiltprojectartbook.roomdb.ArtDao
import com.hakancevik.hiltprojectartbook.util.Resource
import javax.inject.Inject

class ArtRepository @Inject constructor(
    private val artDao: ArtDao,
    private val pixabayAPI: PixabayAPI
) : ArtRepositoryInterface {

    override suspend fun insertArt(art: Art) {
        artDao.insertArt(art)
    }


    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }


    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeArts()
    }


    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return try {
            val response = pixabayAPI.imageSearch(imageString)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            } else {
                Resource.error("Error", null)
            }

        } catch (e: Exception) {
            Resource.error("No Data!", null)
        }

    }


}