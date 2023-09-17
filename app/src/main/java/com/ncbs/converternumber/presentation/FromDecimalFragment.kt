package com.ncbs.converternumber.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ncbs.converternumber.databinding.FragmentFromDecimalBinding
import kotlinx.coroutines.launch

class FromDecimalFragment : Fragment() {
    private var _binding: FragmentFromDecimalBinding? = null
    private val binding: FragmentFromDecimalBinding get() = _binding!!
    private val viewModel: ResultViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFromDecimalBinding.inflate(inflater, container, false)
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
            viewModel.sourceBase.collect {
                viewModel.sourceBase.value
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.result.collect {
                binding.tvResultFrom.text = it
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
            viewModel.sourceBase.value = binding.sourceBase.text.toString()
            viewModel.resultFromDecimal()
            Log.d("xxx", "Clicked ${viewModel.resultFromDecimal()}")
            Log.d("xxx", "viewModel from $viewModel")

        }
        binding.btClear.setOnClickListener {
            viewModel.clearOnClicked()
            binding.sourceNumber.text = null
            binding.sourceBase.text = null
            binding.tvResultFrom.text = null
            Log.d("xxx", "ClickedClear ${viewModel.clearOnClicked()}")
            Log.d("xxx", "viewModel to $viewModel")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}