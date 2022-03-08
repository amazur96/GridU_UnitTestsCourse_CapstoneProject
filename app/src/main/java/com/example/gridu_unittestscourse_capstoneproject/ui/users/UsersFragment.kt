package com.example.gridu_unittestscourse_capstoneproject.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gridu_unittestscourse_capstoneproject.databinding.FragmentUsersBinding

class UsersFragment : Fragment() {

    private lateinit var binding: FragmentUsersBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsersBinding.inflate(inflater)
        return binding.root
    }
}