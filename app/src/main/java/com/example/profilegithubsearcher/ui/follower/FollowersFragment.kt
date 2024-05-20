package com.example.profilegithubsearcher.ui.follower

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profilegithubsearcher.adapter.ListUserAdapter
import com.example.profilegithubsearcher.adapter.SectionsPagerAdapter.Companion.ARGS_USERNAME
import com.example.profilegithubsearcher.data.repsonse.User
import com.example.profilegithubsearcher.databinding.FragmentFollowersBinding
import com.example.profilegithubsearcher.ui.detail.DetailUserActivity

class FollowersFragment : Fragment() {

    private lateinit var binding: FragmentFollowersBinding

    private val followerViewModel by viewModels<FollowerViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followerViewModel.listFollower.observe(viewLifecycleOwner) { follower ->
            if (follower.isEmpty()) {
                binding.tvStatusUsers.visibility = View.VISIBLE
            } else {
                showRecycleView(follower)
            }

        }

        followerViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }


        followerViewModel.isError.observe(viewLifecycleOwner) { error ->
            if (error) errorHandler()
        }

        val username = arguments?.getString(ARGS_USERNAME)
        if (username != null) {
            followerViewModel.fetchFollowers(username)
        }

    }


    private fun showLoading(state: Boolean) {
        if (state) {
            binding.pbLoading.visibility = View.VISIBLE
        } else {
            binding.pbLoading.visibility = View.GONE
        }
    }


    private fun errorHandler() {
        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
    }

    private fun showRecycleView(user: ArrayList<User>) {
        val listUserAdapter = ListUserAdapter(user)
        binding.rvFollowers.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listUserAdapter
            setHasFixedSize(true)
        }

        listUserAdapter.setOnItemClickCallBack(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val intent = Intent(context, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_USER, data.login)
                startActivity(intent)
            }
        })
    }
}