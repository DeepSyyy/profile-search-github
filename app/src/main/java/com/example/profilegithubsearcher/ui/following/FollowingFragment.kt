package com.example.profilegithubsearcher.ui.following

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profilegithubsearcher.adapter.ListUserAdapter
import com.example.profilegithubsearcher.adapter.SectionsPagerAdapter
import com.example.profilegithubsearcher.data.repsonse.User
import com.example.profilegithubsearcher.databinding.FragmentFollowingBinding
import com.example.profilegithubsearcher.ui.detail.DetailUserActivity

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    private val followingViewModel by viewModels<FollowingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followingViewModel.listFollower.observe(viewLifecycleOwner){follower ->
            if (follower.isEmpty()){
                binding.tvStatusUsers.visibility = View.VISIBLE
            }else{
                showRecycleView(follower)
            }

        }

        followingViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }


        followingViewModel.isError.observe(viewLifecycleOwner){ error ->
            if(error) errorHandler()
        }

        val username = arguments?.getString(SectionsPagerAdapter.ARGS_USERNAME)
        if (username != null) {
            followingViewModel.fetchFollowing(username)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun showLoading(state: Boolean) {
        if (state) {
            binding.pbLoading.visibility = View.VISIBLE
        } else {
            binding.pbLoading.visibility = View.GONE
        }
    }


    private fun errorHandler(){
        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
    }

    private fun showRecycleView(user: ArrayList<User>) {
        val listUserAdapter = ListUserAdapter(user)
        binding.rvFollowing.apply {
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