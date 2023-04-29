package com.hakancevik.hiltprojectartbook.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.hakancevik.hiltprojectartbook.databinding.FragmentArtDetailsBinding
import com.hakancevik.hiltprojectartbook.util.Status
import com.hakancevik.hiltprojectartbook.viewmodel.ArtViewModel


import javax.inject.Inject

class ArtDetailsFragment @Inject constructor(
    val glide: RequestManager
) : Fragment() {


    private var _binding: FragmentArtDetailsBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: ArtViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArtDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)
        subscribeToObservers()

        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        binding.selectImageText.setOnClickListener {
            val action = ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApiFragment()
            findNavController().navigate(action)
        }

        binding.artImageView.setOnClickListener {
            val action = ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApiFragment()
            findNavController().navigate(action)
        }

        onBackPressedCallback()


        binding.saveButton.setOnClickListener {
            Log.d("system.out", "clicked button save")
            viewModel.makeArt(
                binding.nameText.text.toString().trim(),
                binding.artistText.text.toString().trim(),
                binding.yearText.text.toString().trim()
            )

        }


    }

    private fun onBackPressedCallback() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
                viewModel.selectImageText.value = true
                viewModel.selectImageView.value = false
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }


    private fun subscribeToObservers() {

        viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer { url ->

            Log.d("system.out", "callll ")

            binding.let {
                glide.load(url).into(it.artImageView)
            }
        })

        viewModel.inserArtMessage.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                    viewModel.resetInsertArtMsg()

                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message ?: "Error", Toast.LENGTH_SHORT).show()
                }

                Status.LOADING -> {

                }

            }

        })


        viewModel.selectImageText.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    binding.selectImageText.visibility = View.VISIBLE
                } else {
                    binding.selectImageText.visibility = View.GONE
                }

            }
        })

        viewModel.selectImageView.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    binding.artImageView.visibility = View.VISIBLE
                } else {
                    binding.artImageView.visibility = View.GONE
                }

            }
        })


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}