package com.hakancevik.hiltprojectartbook.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hakancevik.hiltprojectartbook.model.Art
import com.hakancevik.hiltprojectartbook.model.ImageResponse
import com.hakancevik.hiltprojectartbook.util.Resource

class FakeArtRepository : ArtRepositoryInterface {

    private val arts = mutableListOf<Art>()
    private val artsLiveData = MutableLiveData<List<Art>>(arts)


    override suspend fun insertArt(art: Art) {
        arts.add(art)
        refreshData()
    }

    override suspend fun deleteArt(art: Art) {
        arts.remove(art)
        refreshData()
    }

    override fun getArt(): LiveData<List<Art>> {
        return artsLiveData
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return Resource.success(ImageResponse(listOf(), 0, 0))
    }

    private fun refreshData() {
        artsLiveData.value = arts
    }

}