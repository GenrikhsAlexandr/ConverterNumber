package com.ncbs.converternumber

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ncbs.converternumber.databinding.FragmentFromDecemberBinding

class FromDecemberFragment : Fragment() {
    private var _binding: FragmentFromDecemberBinding? = null
    private val binding: FragmentFromDecemberBinding get() = _binding!!
    private lateinit var viewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFromDecemberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}