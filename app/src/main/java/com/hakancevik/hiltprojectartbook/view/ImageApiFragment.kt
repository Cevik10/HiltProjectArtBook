package com.hakancevik.hiltprojectartbook.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.hakancevik.hiltprojectartbook.adapter.ImageRecyclerAdapter
import com.hakancevik.hiltprojectartbook.databinding.FragmentImageApiBinding

import com.hakancevik.hiltprojectartbook.util.Status
import com.hakancevik.hiltprojectartbook.viewmodel.ArtViewModel

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageApiFragment @Inject constructor(
    private val imageRecyclerAdapter: ImageRecyclerAdapter
) : Fragment() {

    private var _binding: FragmentImageApiBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: ArtViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImageApiBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchText.requestFocus()

        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.searchText, InputMethodManager.SHOW_IMPLICIT)


        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)
        subscribeToObservers()


        var job: Job? = null
        binding.searchText.addTextChangedListener {

            job?.cancel()
            job = lifecycleScope.launch {
                delay(1000)
                it?.let {
                    if (it.toString().isNotEmpty()) {
                        viewModel.searchForImage(it.toString())
                    }
                }
            }

        }



        binding.imageRecyclerView.adapter = imageRecyclerAdapter
        binding.imageRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)

        imageRecyclerAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setSelectedImage(it)
        }


    }

    private fun subscribeToObservers() {

        viewModel.imageList.observe(viewLifecycleOwner, Observer {

            when (it.status) {
                Status.SUCCESS -> {
                    val urls = it.data?.hits?.map { imageResult -> imageResult.previewURL }

                    imageRecyclerAdapter.images = urls ?: listOf()
                    binding.progressBar.visibility = View.GONE
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message ?: "Error", Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE
                }

                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE

                }


            }


        })

        viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer {


        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}