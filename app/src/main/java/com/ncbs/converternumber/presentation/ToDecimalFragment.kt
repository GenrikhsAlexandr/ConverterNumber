package com.ncbs.converternumber.presentation

import android.os.Bundle
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
        onClickListener()
        subscribe()
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

    private fun onClickListener() {
        binding.btResultToDecember.setOnClickListener {
            viewModel.sourceNumber.value = binding.sourceNumber.text.toString()
            viewModel.sourceBase.value = binding.sourceBase.text.toString()
            viewModel.toDecimal()

        }
        binding.btClear.setOnClickListener {
            viewModel.clearOnClicked()
            binding.sourceNumber.text = null
            binding.sourceBase.text = null
            binding.tvResultTo.text = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}