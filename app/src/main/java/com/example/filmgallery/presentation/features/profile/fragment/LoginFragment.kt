package com.example.filmgallery.presentation.features.profile.fragment

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
import com.example.filmgallery.R
import com.example.filmgallery.databinding.FragmentLoginBinding
import com.example.filmgallery.presentation.features.profile.viewmodel.LoginViewModel
import com.example.filmgallery.utils.CommonUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding? = null
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initObservers()
        setupScreen()
    }

    override fun onResume() {
        super.onResume()
        CommonUtils.enableNavigationButton(activity, 2)
    }

    private fun setupScreen() {
        binding?.run {
            tvRegister.setOnClickListener {
                findNavController().navigate(R.id.registerFragment)
            }
            btnEnter.setOnClickListener {
                if (tietName.text.toString() != "" && tietPassword.text.toString() != "") {
                    setupElementsVisibility(true)
                    viewModel.login(
                        tietName.text.toString(),
                        tietPassword.text.toString()
                    )
                } else CommonUtils.makeToast(
                    context,
                    getString(R.string.empty_fields)
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(
                Lifecycle.State.STARTED
            ) {
                launch {
                    viewModel.uiLogin.collect {
                        when (it) {
                            null -> {
                                setupElementsVisibility(false)
                            }
                            true -> {
                                findNavController().navigate(
                                    R.id.profileFragment
                                )
                            }
                            false -> {
                                setupElementsVisibility(false)
                                CommonUtils.makeToast(
                                    context,
                                    getString(R.string.no_such_user)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupElementsVisibility(isLoading: Boolean) {
        binding?.run {
            layoutLogin.visibility = if (isLoading) View.GONE else View.VISIBLE
            layoutLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}
