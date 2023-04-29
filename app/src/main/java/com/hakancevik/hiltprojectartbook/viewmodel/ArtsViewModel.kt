package com.hakancevik.hiltprojectartbook.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hakancevik.hiltprojectartbook.model.Art
import com.hakancevik.hiltprojectartbook.repo.ArtRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtsViewModel @Inject constructor(
    private val repository: ArtRepositoryInterface
) : ViewModel() {

    val artList = repository.getArt()

    fun deleteArt(art: Art) = viewModelScope.launch {
        repository.deleteArt(art)
    }

}