package com.example.gridu_unittestscourse_capstoneproject.ui.users

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gridu_unittestscourse_capstoneproject.databinding.FragmentUsersBinding
import com.example.gridu_unittestscourse_capstoneproject.ui.MainActivity
import com.example.gridu_unittestscourse_capstoneproject.ui.users.adapter.UsersAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersFragment : Fragment() {

    private lateinit var binding: FragmentUsersBinding
    private val viewModel by viewModels<UsersViewModel>()

    private val adapter = UsersAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsersBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUsersRecyclerView()
        observeData()
        viewModel.getUsers()
    }

    private fun observeData() {
        with(viewModel) {
            loading.observe(viewLifecycleOwner) {
                getMainActivity().setProgressBar(it)
            }
            userList.observe(viewLifecycleOwner) {
                adapter.setUserList(it!!)
            }
            emptyResponse.observe(viewLifecycleOwner) {
                with(binding) {
                    usersRecyclerView.visibility = View.GONE
                    errorMessageFrameLayout.visibility = View.GONE
                    emptyMessageFrameLayout.visibility = if (it) View.VISIBLE else View.GONE
                }
            }
            error.observe(viewLifecycleOwner) {
                with(binding) {
                    errorMessageFrameLayout.visibility = View.VISIBLE
                    errorMessageLayout.errorMessageTextView.text = it.message
                }
            }
        }
    }

    private fun initUsersRecyclerView() {
        binding.usersRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@UsersFragment.adapter
        }
        adapter.onItemClick = {
            openUserDetails(it)
        }
    }

    private fun openUserDetails(userId: Int) {
        findNavController().navigate(
            UsersFragmentDirections.actionUsersFragmentToUserDetailFragment(userId)
        )
    }

    private fun getMainActivity(): MainActivity = activity as MainActivity
}