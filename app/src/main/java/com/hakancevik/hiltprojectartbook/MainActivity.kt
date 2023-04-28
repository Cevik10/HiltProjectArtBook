package com.hakancevik.hiltprojectartbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hakancevik.hiltprojectartbook.common.ArtFragmentFactory
import com.hakancevik.hiltprojectartbook.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory


    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        supportFragmentManager.fragmentFactory = fragmentFactory
        setContentView(view)


    }
}