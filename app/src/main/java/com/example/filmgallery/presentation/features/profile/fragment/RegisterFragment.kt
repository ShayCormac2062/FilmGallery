package com.example.filmgallery.presentation.features.profile.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.filmgallery.R
import com.example.filmgallery.databinding.FragmentRegisterBinding
import com.example.filmgallery.domain.model.User
import com.example.filmgallery.presentation.features.profile.viewmodel.RegisterViewModel
import com.example.filmgallery.utils.CommonUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var binding: FragmentRegisterBinding? = null
    private var imageUrl: String = ""
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initObservers()
        setupScreen()
        CommonUtils.enableNavigationButton(activity, 2)
        checkPermissions()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(
                Lifecycle.State.CREATED
            ) {
                launch {
                    viewModel.uiRegister.collect {
                        when(it) {
                            false -> {
                                onRequestUpdate(false)
                                CommonUtils.makeToast(context, getString(R.string.register_error))
                            }
                            true -> {
                                CommonUtils.makeToast(context, getString(R.string.register_success))
                                findNavController().navigate(
                                    R.id.profileFragment
                                )
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        imageUrl = ""
    }

    private fun setupScreen() {
        binding?.run {
            btnRegister.setOnClickListener {
                if (isNoEmptyFields()) {
                    if (tietPassword.text.toString() == tietRepeatPassword.text.toString()) {
                        onRequestUpdate(true)
                        viewModel.register(
                            User(
                                0,
                                tietFirstName.text.toString(),
                                tietLastName.text.toString(),
                                tietUserName.text.toString(),
                                imageUrl,
                                tietPassword.text.toString(),
                                true
                            )
                        )
                    } else CommonUtils.makeToast(context, getString(R.string.dont_same_passwords))
                } else CommonUtils.makeToast(context, getString(R.string.empty_fields))
            }
            btnGetPhoto.setOnClickListener {
                selectImageFromGallery()
            }
        }
        enableNavigationButton()
    }

    private fun onRequestUpdate(isLoading: Boolean) {
        binding?.run {
            layoutLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
            layoutRegistration.visibility = if (isLoading) View.GONE else View.VISIBLE
        }
    }

    private fun enableNavigationButton() {
        activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)
            ?.menu
            ?.getItem(2)
            ?.isChecked = true
    }

    private fun isNoEmptyFields(): Boolean {
        return binding?.run {
            tietFirstName.text.toString() != "" &&
                    tietLastName.text.toString() != "" &&
                    tietUserName.text.toString() != "" &&
                    tietPassword.text.toString() != "" &&
                    tietRepeatPassword.text.toString() != ""
        } == true
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED) {
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {}.launch(
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    private fun selectImageFromGallery() {
        val i = Intent(Intent.ACTION_PICK)
        i.type = "image/*"
        startActivityForResult(i, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 100) {
                imageUrl = data?.data.toString()
                binding?.btnGetPhoto?.isActivated = true
                binding?.btnGetPhoto?.text = getString(R.string.photo_selected)
            }
        } else CommonUtils.makeToast(context, getString(R.string.denied_permission_notification))
    }

}
