package com.hakancevik.hiltprojectartbook.view

import android.content.ClipData.Item
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hakancevik.hiltprojectartbook.adapter.ArtRecyclerAdapter

import com.hakancevik.hiltprojectartbook.databinding.FragmentArtsBinding
import com.hakancevik.hiltprojectartbook.viewmodel.ArtsViewModel
import javax.inject.Inject

class ArtsFragment @Inject constructor(
    val artRecyclerAdapter: ArtRecyclerAdapter
) : Fragment() {

    private var _binding: FragmentArtsBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: ArtsViewModel

    private val swipeCallBack = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.RIGHT) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition = viewHolder.layoutPosition
            val selectedArt = artRecyclerAdapter.arts[layoutPosition]
            viewModel.deleteArt(selectedArt)
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentArtsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ArtsViewModel::class.java)

        subscribeToObservers()

        binding.recyclerViewArt.adapter = artRecyclerAdapter
        binding.recyclerViewArt.layoutManager = LinearLayoutManager(requireContext())
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.recyclerViewArt)

        binding.fab.setOnClickListener {
            val action = ArtsFragmentDirections.actionArtsFragmentToArtDetailsFragment()
            findNavController().navigate(action)
        }


    }

    private fun subscribeToObservers() {
        viewModel.artList.observe(viewLifecycleOwner, Observer {
            artRecyclerAdapter.arts = it
        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}