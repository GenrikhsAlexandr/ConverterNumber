package com.ncbs.converternumber.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ncbs.converternumber.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    private val viewModel: ResultViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
        setClickListener()
    }

    private fun subscribe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.sourceNumber.collect {
                viewModel.sourceNumber.value
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.sourceBase1.collect {
                viewModel.sourceBase1.value
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.sourceBase2.collect {
                viewModel.sourceBase2.value
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.result.collect {
                binding.tvResult.text = it
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.invalid.collect { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.emptyNumber.collect { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setClickListener() {
        binding.btResultFromDecember.setOnClickListener {
            viewModel.sourceNumber.value = binding.sourceNumber.text.toString()
            viewModel.sourceBase1.value = binding.sourceBase1.text.toString()
            viewModel.sourceBase2.value = binding.sourceBase2.text.toString()
            viewModel.resultMixSystem()
        }

        binding.btClear.setOnClickListener {
            viewModel.clearOnClicked()
            binding.sourceNumber.text = null
            binding.sourceBase1.text = null
            binding.sourceBase2.text = null
            binding.tvResult.text = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}