package com.hakancevik.hiltprojectartbook.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hakancevik.hiltprojectartbook.model.Art
import com.hakancevik.hiltprojectartbook.repo.ArtRepositoryInterface
import com.hakancevik.hiltprojectartbook.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class ArtDetailsViewModel @Inject constructor(
    private val repository: ArtRepositoryInterface
) : ViewModel() {


    private var insertArtMsg = MutableLiveData<Resource<Art>>()
    val inserArtMessage: LiveData<Resource<Art>>
        get() = insertArtMsg


    fun resetInsertArtMsg() {
        insertArtMsg = MutableLiveData<Resource<Art>>()
    }


    fun deleteArt(art: Art) = viewModelScope.launch {
        repository.deleteArt(art)
    }


    fun insertArt(art: Art) = viewModelScope.launch {
        repository.insertArt(art)
    }

    private val selectedImage = MutableLiveData<String>()
    val selectedImageUrl: LiveData<String>
        get() = selectedImage

    fun setSelectedImage(url: String) {
        selectedImage.postValue(url)
        //selectedImage.value = url
    }

    fun makeArt(name: String, artistName: String, year: String) {
        if (name.isEmpty() || artistName.isEmpty() || year.isEmpty()) {
            insertArtMsg.postValue(Resource.error("Please enter blanks..", null))
            return
        }

        var yearInt = try {
            year.toInt()
        } catch (e: Exception) {
            insertArtMsg.postValue(Resource.error("Year should be number!", null))
            return
        }


        val art = Art(name, artistName, yearInt, selectedImage.value ?: "")
        insertArt(art)
        setSelectedImage("")
        insertArtMsg.postValue(Resource.success(art))

    }


}