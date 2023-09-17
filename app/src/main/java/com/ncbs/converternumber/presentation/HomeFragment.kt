package com.ncbs.converternumber.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ncbs.converternumber.R
import com.ncbs.converternumber.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fromDecember.setOnClickListener {
            fromDecember()
        }
        binding.toDecember.setOnClickListener {
            toDecember()
        }
    }

    private fun toDecember(){
        findNavController().navigate(R.id.action_homeFragment_to_toDecemberFragment)
    }
    private fun fromDecember(){
        findNavController().navigate(R.id.action_homeFragment_to_fromDecemberFragment)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}