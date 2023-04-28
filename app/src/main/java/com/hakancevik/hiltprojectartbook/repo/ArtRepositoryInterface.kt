package com.hakancevik.hiltprojectartbook.repo

import androidx.lifecycle.LiveData
import com.hakancevik.hiltprojectartbook.model.Art
import com.hakancevik.hiltprojectartbook.model.ImageResponse
import com.hakancevik.hiltprojectartbook.util.Resource


interface ArtRepositoryInterface {

    suspend fun insertArt(art: Art)

    suspend fun deleteArt(art: Art)

    fun getArt(): LiveData<List<Art>>

    suspend fun searchImage(imageString: String): Resource<ImageResponse>


}