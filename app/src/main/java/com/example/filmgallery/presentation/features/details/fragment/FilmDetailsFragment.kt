package com.example.filmgallery.presentation.features.details.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.filmgallery.R
import com.example.filmgallery.databinding.FragmentDetailsBinding
import com.example.filmgallery.domain.model.Actor
import com.example.filmgallery.domain.model.Film
import com.example.filmgallery.domain.model.Genre
import com.example.filmgallery.presentation.adapter.actors.ActorsAdapter
import com.example.filmgallery.presentation.features.details.viewmodel.FilmDetailsViewModel
import com.example.filmgallery.utils.CommonUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FilmDetailsFragment : Fragment() {

    private var binding: FragmentDetailsBinding? = null
    private val viewModel: FilmDetailsViewModel by viewModels()
    private var onFilmAdd: (() -> Unit)? = null
    private var onFilmDelete: (() -> Unit)? = null
    private var actorsAdapter: ActorsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initObservers()
        initAdapter()
        CommonUtils.enableNavigationButton(
            activity,
            arguments?.getInt("NAV_ID") ?: 0
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        onFilmDelete = null
        onFilmAdd = null
        actorsAdapter = null
    }

    private fun initAdapter() {
        actorsAdapter = ActorsAdapter(requireContext())
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(
                Lifecycle.State.CREATED
            ) {
                launch {
                    viewModel.uiState.collect {
                        when (it) {
                            null -> onLoadingScreen()
                            else -> {
                                onSuccess(it)
                            }
                        }
                    }
                }
                launch {
                    viewModel.uiError.collect {
                        if (it == true) {
                            onError {
                                viewModel.getFilmById(id)
                            }
                        }
                    }
                }
                launch {
                    viewModel.uiUserName.collect {
                        if (it != null && it != "null") {
                            binding?.btnFavorite?.visibility = View.VISIBLE
                        }
                        viewModel.getLocalFilmById(arguments?.getInt("ID") ?: 0)
                    }
                }
                launch {
                    viewModel.uiFavorite.collect {
                        if (it != null) {
                            setupFavoritesButton(it)
                        }
                    }
                }
                launch {
                    viewModel.uiActors.collect {
                        when(it) {
                            null -> onLoadingActors()
                            emptyList<Actor>() -> onEmptyActors()
                            else -> onSuccessActors(it)
                        }
                    }
                }
            }
        }
    }

    private fun onSuccessActors(actors: List<Actor>) {
        binding?.run {
            pbLoadingActors.visibility = View.GONE
            rvActors.adapter = actorsAdapter?.apply {
                submitList(actors.toMutableList())
            }
        }
    }

    private fun onEmptyActors() {
        binding?.run {
            pbLoadingActors.visibility = View.GONE
            tvActors.text = getString(R.string.empty_actors)
        }
    }

    private fun onLoadingActors() {
        binding?.run {
            pbLoadingActors.visibility = View.VISIBLE
        }
    }

    private fun setupFavoritesButton(isAddedToFavorite: Boolean) {
        binding?.btnFavorite?.run {
            if (isAddedToFavorite) {
                setBackgroundResource(R.drawable.star_filled)
                setOnClickListener {
                    onFilmDelete?.invoke()
                    CommonUtils.makeToast(context, getString(R.string.film_removed_notification))
                }
            } else {
                setBackgroundResource(R.drawable.star_outlined)
                setOnClickListener {
                    onFilmAdd?.invoke()
                    CommonUtils.makeToast(context, getString(R.string.film_added_notification))
                }
            }
        }
    }

    private fun onSuccess(film: Film) {
        binding?.run {
            layoutDetails.visibility = View.VISIBLE
            layoutError.visibility = View.GONE
            layoutLoading.visibility = View.GONE
            ivBackdrop.load(
                String.format(context?.getString(R.string.image_url).toString(), film.path)
            )
            tvFilmName.text = film.originalTitle
            tvFilmDescription.text = film.review
            tvFilmRuntime.text = String.format(
                context?.getString(R.string.runtime_value).toString(), film.runtime
            )
            tvFilmDateOfCreation.text = film.releaseDate
            ivPoster.load(
                String.format(context?.getString(R.string.image_url).toString(), film.posterPath)
            )
            tvGenres.text = getGenresString(film.genres)
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            onFilmAdd = {
                viewModel.addFilm(film)
            }
            onFilmDelete = {
                viewModel.deleteFilm(film.filmId)
            }
        }
    }

    private fun getGenresString(genres: List<Genre>): String {
        if (genres.isNotEmpty()) {
            val builder = java.lang.StringBuilder()
            for (i in 0 until genres.size - 1) {
                builder.append(genres[i].name).append(", ")
            }
            builder.append(genres[genres.size - 1].name)
            return builder.toString()
        }
        return getString(R.string.no_genres)
    }

    private fun onError(
        onClick: () -> Unit
    ) {
        binding?.run {
            layoutDetails.visibility = View.GONE
            layoutError.visibility = View.VISIBLE
            layoutLoading.visibility = View.GONE
            tvError.text = getString(R.string.error_default)
            btnConfirm.setOnClickListener {
                onClick.invoke()
            }
        }
    }

    private fun onLoadingScreen() {
        binding?.run {
            layoutDetails.visibility = View.GONE
            layoutError.visibility = View.GONE
            layoutLoading.visibility = View.VISIBLE
        }
    }

}
