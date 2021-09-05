package com.example.testapplication.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.GONE
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.domain.model.ImageSize
import com.example.domain.model.PhotoResult
import com.example.testapplication.R
import com.example.testapplication.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment: Fragment() {
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpSearchViewAndDisplay()
        setUpPageCount()
    }

    private fun setUpPageCount() {
        viewModel.pageCount.observe(viewLifecycleOwner) { pageDisplay ->
            binding.pageCount.text = getString(R.string.page_count, pageDisplay.first, pageDisplay.second)
            binding.pageBack.apply {
                visibility = if (pageDisplay.first > 1) View.VISIBLE else View.GONE
                setOnClickListener {
                    viewModel.getPreviousPage()
                }
            }
            binding.pageForward.apply {
                visibility = if (pageDisplay.first != pageDisplay.second) View.VISIBLE else View.GONE
                setOnClickListener {
                    viewModel.getNextPage()
                }
            }
        }
    }

    private fun setUpSearchViewAndDisplay() {
        val adapter = GridAdapter(::onItemClicked)
        binding.imageList.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.imageList.adapter = adapter
        binding.search.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(input: String): Boolean {
                search(input)
                return false
            }

            override fun onQueryTextChange(input: String): Boolean {
                when {
                    input.length >= 3 -> search(input)
                    input.isEmpty() -> resetUi(adapter)
                }
                return false
            }

            private fun search(text: String) {
                viewModel.searchImages(text).observe(viewLifecycleOwner) {
                    adapter.setItems(it)
                }
            }
        })
    }

    private fun resetUi(adapter: GridAdapter) {
        adapter.setItems(listOf())
        binding.pageForward.visibility = GONE
        binding.pageBack.visibility = GONE
        binding.pageCount.text = ""
    }

    private fun onItemClicked(image: PhotoResult) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(
            image.getUrl(ImageSize.MEDIUM),
            image.title
        )
        findNavController().navigate(action)
    }
}