package com.example.profilegithubsearcher.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.profilegithubsearcher.R
import com.example.profilegithubsearcher.adapter.SectionsPagerAdapter
import com.example.profilegithubsearcher.data.local.entity.UserEntity
import com.example.profilegithubsearcher.data.repsonse.DetailUserResponse
import com.example.profilegithubsearcher.databinding.ActivityDetailUserBinding
import com.example.profilegithubsearcher.ui.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailUserBinding

    private val factory by lazy { ViewModelFactory.getInstance(this) }

    private val detailViewModel: DetailViewModel by viewModels { factory }


    companion object {
        @StringRes
        private val TAB_TITTLES = intArrayOf(
            R.string.first_tab,
            R.string.second_tab
        )
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user = intent.getStringExtra(EXTRA_USER)

        detailViewModel.userResponse.observe(this) {
            setUpBind(it)
            setUpViewPager(it.login)
            detailViewModel.getUserById(it.login, this)
        }

        detailViewModel.isFavorite.observe(this) {
            isFavorite(it)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.isError.observe(this) { error ->
            if (error) errorHandler(error.toString())
        }
        if (user != null) {
            getUserDetail(user)
        }
        setupToolbar()
        binding.fabFavorite.setOnClickListener(this)
    }

    private fun isFavorite(isFavorite: Boolean) {
        if (isFavorite) {
            binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_24)
        } else {
            binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
        }
    }


    private fun setupToolbar() {
        binding.apply {
            appbar.toolbar.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }


    private fun errorHandler(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    private fun getUserDetail(user: String) {
        detailViewModel.getUserDetail(user)
    }

    private fun setUpBind(user: DetailUserResponse) {
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
            binding.pbLoading.visibility = View.VISIBLE
        } else {
            binding.pbLoading.visibility = View.GONE
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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fab_favorite -> {
                if (detailViewModel.isFavorite.value == true) {
                    deleteFavorite()
                    Toast.makeText(this, "User Removed from Favorite", Toast.LENGTH_SHORT).show()
                } else {
                    setFavorite()
                    Toast.makeText(this, "User Added to Favorite", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setFavorite() {

        detailViewModel.saveAsFavUser(
            UserEntity(
                login = detailViewModel.userResponse.value?.login.orEmpty(),
                avatarUrl = detailViewModel.userResponse.value?.avatarUrl,
                isFavorite = true
            )
        )
    }

    private fun deleteFavorite() {
        detailViewModel.removeFavUser(detailViewModel.userResponse.value?.login.orEmpty())
    }
}