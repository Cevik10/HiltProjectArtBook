package com.hakancevik.hiltprojectartbook.common

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.hakancevik.hiltprojectartbook.adapter.ArtRecyclerAdapter
import com.hakancevik.hiltprojectartbook.adapter.ImageRecyclerAdapter
import com.hakancevik.hiltprojectartbook.view.ArtDetailsFragment
import com.hakancevik.hiltprojectartbook.view.ArtsFragment
import com.hakancevik.hiltprojectartbook.view.ImageApiFragment
import javax.inject.Inject

class ArtFragmentFactory @Inject constructor(
    private val artRecyclerAdapter: ArtRecyclerAdapter,
    private val imageRecyclerAdapter: ImageRecyclerAdapter,
    private val glide: RequestManager
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when (className) {
            ArtsFragment::class.java.name -> ArtsFragment(artRecyclerAdapter)
            ArtDetailsFragment::class.java.name -> ArtDetailsFragment(glide)
            ImageApiFragment::class.java.name -> ImageApiFragment(imageRecyclerAdapter)
            else -> super.instantiate(classLoader, className)
        }


    }

}