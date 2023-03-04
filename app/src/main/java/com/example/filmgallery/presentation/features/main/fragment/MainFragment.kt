package com.example.filmgallery.presentation.features.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.filmgallery.R
import com.example.filmgallery.databinding.FragmentMainBinding
import com.example.filmgallery.domain.model.BaseElement
import com.example.filmgallery.presentation.adapter.elements.ElementsAdapter
import com.example.filmgallery.presentation.features.main.viewmodel.ListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var binding: FragmentMainBinding? = null
    private val viewModel: ListViewModel by viewModels()
    private var adapter: ElementsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initObservers()
        initAdapter()
    }

    private fun initAdapter() {
        adapter = ElementsAdapter(requireContext()) { id, label ->
            findNavController().navigate(
                if (label == getString(R.string.label_default))
                    R.id.serialDetailsFragment
                else R.id.detailsFragment,
                bundleOf(
                    "ID" to id,
                    "NAV_ID" to 0
                )
            )
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(
                Lifecycle.State.STARTED
            ) {
                launch {
                    viewModel.uiState.collect {
                        when (it) {
                            null -> onLoading()
                            arrayListOf<BaseElement>() -> onError {
                                onLoading()
                                onViewModelInit()
                            }
                            else -> {
                                onSuccess()
                                setupScreen(it)
                            }
                        }
                    }
                }
                launch {
                    viewModel.uiError.collect {
                        if (it != null) {
                            onError(it) {
                                onLoading()
                                onViewModelInit()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupScreen(elements: ArrayList<BaseElement>) {
        binding?.rvElements?.adapter = adapter?.apply {
            submitList(elements)
        }
    }

    private fun onViewModelInit() {
        with(viewModel) {
            getFilms(isNeedToUpdate = true)
            getSerials()
        }
    }

    private fun onSuccess() {
        binding?.run {
            layoutList.visibility = View.VISIBLE
            layoutError.visibility = View.GONE
            layoutLoading.visibility = View.GONE
        }
    }

    private fun onLoading() {
        binding?.run {
            layoutList.visibility = View.GONE
            layoutError.visibility = View.GONE
            layoutLoading.visibility = View.VISIBLE
        }
    }

    private fun onError(
        errorMessage: String? = context?.getString(R.string.error_default),
        onClick: () -> Unit
    ) {
        binding?.run {
            layoutList.visibility = View.GONE
            layoutError.visibility = View.VISIBLE
            layoutLoading.visibility = View.GONE
            btnConfirm.setOnClickListener {
                onClick.invoke()
            }
            tvError.text = errorMessage
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        adapter = null
    }

}
