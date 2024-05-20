package com.example.profilegithubsearcher.ui.favorite

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profilegithubsearcher.adapter.FavoriteUserAdapter
import com.example.profilegithubsearcher.data.local.entity.UserEntity
import com.example.profilegithubsearcher.databinding.ActivityFavoriteBinding
import com.example.profilegithubsearcher.ui.ViewModelFactory
import com.example.profilegithubsearcher.ui.detail.DetailUserActivity
import com.example.profilegithubsearcher.ui.detail.DetailUserActivity.Companion.EXTRA_USER

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    private val factory by lazy { ViewModelFactory.getInstance(this) }
    private val viewModel : FavoriteViewModel by viewModels { factory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()

        viewModel.getFavoriteUser().observe(this, ::showRecycleView)
        if (viewModel.getFavoriteUser().value?.isEmpty() == true) {
            binding.tvNoUsers.visibility = android.view.View.VISIBLE
        } else {
            binding.tvNoUsers.visibility = android.view.View.GONE
        }
    }


    private fun showRecycleView(listUser: List<UserEntity>) {
        val listUserAdapter = FavoriteUserAdapter(listUser, object : FavoriteUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserEntity) {
                val intent = Intent(this@FavoriteActivity, DetailUserActivity::class.java)
                intent.putExtra(EXTRA_USER, data.login)
                startActivity(intent)
            }
        })
        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            adapter = listUserAdapter
            setHasFixedSize(true)
        }
    }

}