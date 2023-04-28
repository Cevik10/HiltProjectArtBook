package com.hakancevik.hiltprojectartbook.viewmodel

import androidx.lifecycle.ViewModel
import com.hakancevik.hiltprojectartbook.repo.ArtRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArtsViewModel @Inject constructor(
    private val repository: ArtRepositoryInterface
) : ViewModel() {

    val artList = repository.getArt()


}