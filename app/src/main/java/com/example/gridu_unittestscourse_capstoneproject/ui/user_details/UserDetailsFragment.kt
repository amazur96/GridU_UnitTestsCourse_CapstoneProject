package com.example.gridu_unittestscourse_capstoneproject.ui.user_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.gridu_unittestscourse_capstoneproject.R
import com.example.gridu_unittestscourse_capstoneproject.data.model.UserDetails
import com.example.gridu_unittestscourse_capstoneproject.databinding.FragmentUserDetailsBinding
import com.example.gridu_unittestscourse_capstoneproject.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailsFragment : Fragment() {

    private lateinit var binding: FragmentUserDetailsBinding
    private val viewModel by viewModels<UserDetailsViewModel>()

    private val args: UserDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        viewModel.getUserDetails(args.userId)
    }

    private fun observeData() {
        with(viewModel) {
            loading.observe(viewLifecycleOwner) {
                getMainActivity()?.setProgressBar(it)
            }
            userDetails.observe(viewLifecycleOwner) {
                it?.let {
                    binding.errorMessageFrameLayout.visibility = View.GONE
                    showUserDetails(it)
                }
            }
            error.observe(viewLifecycleOwner) {
                binding.apply {
                    errorMessageFrameLayout.visibility = View.VISIBLE
                    errorMessageLayout.errorMessageTextView.text = it.message
                }
            }
        }
    }

    private fun showUserDetails(userDetails: UserDetails) {
        binding.apply {
            with(userDetails) {
                nameTextView.text = name
                loginTextView.text = login
                bioTextView.text = bio
                locationTextView.text = if (location.isNullOrEmpty()) getString(R.string.empty_location) else location
                emailTextView.text = if (email.isNullOrEmpty()) getString(R.string.empty_email) else email
                repositoriesCounterTextView.text = public_repos.toString()
                followersCounterTextView.text = followers.toString()
                hireableTextView.text = if (hireable.isNullOrEmpty()) false.toString() else hireable
                Glide.with(requireContext())
                    .load(avatar_url)
                    .into(avatarImageView)
            }
        }
    }

    private fun getMainActivity(): MainActivity? = activity as? MainActivity
}