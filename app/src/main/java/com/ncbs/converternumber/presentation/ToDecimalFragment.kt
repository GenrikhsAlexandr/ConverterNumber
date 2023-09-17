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
import com.ncbs.converternumber.databinding.FragmentToDecimalBinding
import kotlinx.coroutines.launch

class ToDecimalFragment : Fragment() {

    private var _binding: FragmentToDecimalBinding? = null
    private val binding: FragmentToDecimalBinding get() = _binding!!
    private val viewModel: ResultViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentToDecimalBinding.inflate(inflater, container, false)
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
                binding.tvResultTo.text = it
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
        binding.btResultToDecimal.setOnClickListener {
            viewModel.sourceNumber.value = binding.sourceNumber.text.toString()
            viewModel.sourceBase.value = binding.sourceBase.text.toString()
            viewModel.toDecimal()
            Log.d("xxx", "ClickedResult ${viewModel.toDecimal()}")
            Log.d("xxx", "viewModel to $viewModel")


        }
        binding.btClear.setOnClickListener {
            viewModel.clearOnClicked()
            binding.sourceNumber.text = null
            binding.sourceBase.text = null
            binding.tvResultTo.text = null
            Log.d("xxx", "ClickedClear ${viewModel.clearOnClicked()}")
            Log.d("xxx", "viewModel to $viewModel")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}