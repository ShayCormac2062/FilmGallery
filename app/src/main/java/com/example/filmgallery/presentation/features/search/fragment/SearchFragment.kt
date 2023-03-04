package com.example.filmgallery.presentation.features.search.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.filmgallery.R
import com.example.filmgallery.databinding.FragmentSearchBinding
import com.example.filmgallery.domain.model.BaseElement
import com.example.filmgallery.presentation.adapter.elements.ElementsAdapter
import com.example.filmgallery.presentation.features.search.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var binding: FragmentSearchBinding? = null
    private val viewModel: SearchViewModel by viewModels()
    private var adapterForFilms: ElementsAdapter? = null
    private var adapterForSerials: ElementsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initObservers()
        setupScreenElements()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        adapterForSerials = null
        adapterForFilms = null
    }

    private fun setupScreenElements() {
        val context = requireContext()
        binding?.svSearch?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query.toString().isNotEmpty()) onQueryTextUpdated(query)
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        adapterForFilms = ElementsAdapter(context) { id, _ ->
            navigateToDetails(id, R.id.detailsFragment)
        }
        adapterForSerials = ElementsAdapter(context) { id, _ ->
            navigateToDetails(id, R.id.serialDetailsFragment)
        }
    }

    private fun onQueryTextUpdated(query: String?) {
        onLoading()
        with(viewModel) {
            getFilmsBySearch(
                query = query.toString().replace(' ', '+')
            )
            getSerialsBySearch(
                query = query.toString().replace(' ', '+')
            )
        }
    }

    private fun navigateToDetails(id: Int, fragmentId: Int) {
        findNavController().navigate(
            fragmentId,
            bundleOf(
                "ID" to id,
                "NAV_ID" to 1
            )
        )
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(
                Lifecycle.State.STARTED
            ) {
                launch {
                    viewModel.uiFilmState.collect {
                        when (it) {
                            null -> onLoading()
                            else -> {
                                onSuccess()
                                setupFilmsRecycler(it)
                            }
                        }
                    }
                }
                launch {
                    viewModel.uiFilmError.collect {
                        if (it != null) {
                            onError(it)
                        }
                    }
                }
                launch {
                    viewModel.uiSerialState.collect {
                        when (it) {
                            null -> onLoading()
                            else -> {
                                onSuccess()
                                setupSerialsRecycler(it)
                            }
                        }
                    }
                }
                launch {
                    viewModel.uiSerialError.collect {
                        if (it != null) {
                            onError(it)
                        }
                    }
                }
            }
        }
    }

    private fun setupFilmsRecycler(elements: List<BaseElement>) {
        binding?.rvFilms?.adapter = adapterForFilms?.apply {
            submitList(elements.toMutableList())
        }
    }

    private fun setupSerialsRecycler(elements: List<BaseElement>) {
        binding?.rvSerials?.adapter = adapterForSerials?.apply {
            submitList(elements.toMutableList())
        }
    }

    private fun onSuccess() {
        binding?.run {
            layoutLoading.visibility = View.GONE
            layoutSuccess.visibility = View.VISIBLE
            layoutError.visibility = View.GONE
        }
    }

    private fun onError(
        errorMessage: String? = context?.getString(R.string.error_default)
    ) {
        binding?.run {
            layoutLoading.visibility = View.GONE
            layoutSuccess.visibility = View.GONE
            layoutError.visibility = View.VISIBLE
            tvError.text = errorMessage
        }
    }

    private fun onLoading() {
        binding?.run {
            layoutLoading.visibility = View.VISIBLE
            layoutSuccess.visibility = View.GONE
            layoutError.visibility = View.GONE
        }
    }

}
