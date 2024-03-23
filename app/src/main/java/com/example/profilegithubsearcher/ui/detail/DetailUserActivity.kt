package com.example.profilegithubsearcher.ui.detail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.profilegithubsearcher.R
import com.example.profilegithubsearcher.adapter.SectionsPagerAdapter
import com.example.profilegithubsearcher.data.repsonse.DetailUserResponse
import com.example.profilegithubsearcher.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private var _binding: ActivityDetailUserBinding? = null
    private val binding get() = _binding!!

    private val detailViewModel: DetailViewModel by viewModels()

    companion object{
        @StringRes
        private val TAB_TITTLES = intArrayOf(
            R.string.first_tab,
            R.string.second_tab
        )
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)


        detailViewModel.userResponse.observe(this){
            setUpBind(it)
        }

        detailViewModel.isLoading.observe(this){
            showLoading(it)
        }

        detailViewModel.isError.observe(this){ error ->
            if(error) errorHandler(error.toString())
        }

        detailViewModel.userResponse.observe(this){
            setUpViewPager(it.login)
        }

        val user = intent.getStringExtra(EXTRA_USER)
        if (user != null) {
            getUserDetail(user)
        }
        setupToolbar()
    }

    private fun setupToolbar() {
        binding.apply {
            appbar.toolbar.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun errorHandler(error: String){
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    private fun getUserDetail(user: String) {
        detailViewModel.getUserDetail(user)
    }

    private fun setUpBind(user: DetailUserResponse){
        binding.apply {
            tvUsername.text = user.login
            tvLogin.text = user.name
            tvBio.text = user.bio
            tvFollowers.text = user.followers.toString()
            tvFollowing.text = user.following.toString()
            tvPublicRepos.text = user.publicRepos.toString()
            Glide.with(this@DetailUserActivity)
                .load(user.avatarUrl)
                .into(ivAvatar)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.pbLoading.visibility = android.view.View.VISIBLE
        } else {
            binding.pbLoading.visibility = android.view.View.GONE
        }
    }

    private fun setUpViewPager(username: String) {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, username)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITTLES[position])
        }.attach()
    }
}