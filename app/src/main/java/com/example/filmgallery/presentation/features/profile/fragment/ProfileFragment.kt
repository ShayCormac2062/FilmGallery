package com.example.filmgallery.presentation.features.profile.fragment

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
import coil.load
import com.example.filmgallery.R
import com.example.filmgallery.databinding.FragmentProfileBinding
import com.example.filmgallery.domain.model.Film
import com.example.filmgallery.domain.model.User
import com.example.filmgallery.presentation.adapter.elements.ElementsAdapter
import com.example.filmgallery.presentation.features.profile.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var binding: FragmentProfileBinding? = null
    private val viewModel: ProfileViewModel by viewModels()
    private var serialAdapter: ElementsAdapter? = null
    private var filmAdapter: ElementsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initObservers()
        initAdapter()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(
                Lifecycle.State.CREATED
            ) {
                launch {
                    viewModel.uiProfile.collect {
                        when(it) {
                            null -> onLoadingScreen()
                            else -> {
                                onSuccessUser(it)
                                viewModel.getLocalFilms()
                            }
                        }
                    }
                }
                launch {
                    viewModel.uiError.collect {
                        if(it) {
                            findNavController().navigate(
                                R.id.loginFragment
                            )
                        }
                    }
                }
                launch {
                    viewModel.uiFilms.collect {
                        when(it) {
                            emptyList<Film>() -> onEmptyListOfFilms()
                            else -> onSuccessList(it)
                        }
                    }
                }
            }
        }
    }

    private fun onSuccessList(films: List<Film>?) {
        binding?.run {
            tvEmptyFavorites.visibility = View.GONE
            layoutFilms.visibility = View.VISIBLE
            rvFilms.adapter = filmAdapter?.apply {
                submitList(films?.filter {
                    it.label == "Film"
                }?.toMutableList())
            }
            rvSerials.adapter = serialAdapter?.apply {
                submitList(films?.filter {
                    it.label == "Serial"
                }?.toMutableList())
            }
        }
    }

    private fun onEmptyListOfFilms() {
        binding?.run {
            tvEmptyFavorites.visibility = View.VISIBLE
            layoutFilms.visibility = View.GONE
        }
    }

    private fun onSuccessUser(user: User) {
        binding?.run {
            layoutLoading.visibility = View.GONE
            layoutProfile.visibility = View.VISIBLE
            if (user.imageUrl != "") {
                ivCreatorAvatar.load(user.imageUrl)
            }
            tvUsername.text = String.format(
                context?.getString(R.string.profile_data).toString(),
                user.userName,
                user.firstName,
                user.lastName
            )
            btnSignOut.setOnClickListener {
                viewModel.quit(false)
            }
        }
    }

    private fun onLoadingScreen() {
        binding?.run {
            layoutLoading.visibility = View.VISIBLE
            layoutProfile.visibility = View.GONE
        }
    }

    private fun initAdapter() {
        val context = requireContext()
        serialAdapter = ElementsAdapter(context) { id, _ ->
            findNavController().navigate(
                R.id.serialDetailsFragment,
                bundleOf(
                    "ID" to id,
                    "NAV_ID" to 2
                )
            )
        }
        filmAdapter = ElementsAdapter(context) { id, _ ->
            findNavController().navigate(
                R.id.detailsFragment,
                bundleOf(
                    "ID" to id,
                    "NAV_ID" to 2
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        serialAdapter = null
        filmAdapter = null
    }
}
