package com.example.testapplication.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.testapplication.R
import com.example.testapplication.databinding.FragmentImageDetailsBinding

class ImageDetailsFragment : Fragment() {

    lateinit var binding: FragmentImageDetailsBinding
    private val args: ImageDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.apply {
            setupWithNavController(findNavController())
            title = args.title
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
        Glide
            .with(this)
            .load(args.url)
            .placeholder(R.drawable.ic_broken_image)
            .into(binding.image)
    }
}