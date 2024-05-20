package com.example.profilegithubsearcher.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profilegithubsearcher.R
import com.example.profilegithubsearcher.adapter.ListUserAdapter
import com.example.profilegithubsearcher.data.repsonse.User
import com.example.profilegithubsearcher.databinding.ActivityMainBinding
import com.example.profilegithubsearcher.ui.ViewModelFactory
import com.example.profilegithubsearcher.ui.detail.DetailUserActivity

import com.example.profilegithubsearcher.ui.detail.DetailUserActivity.Companion.EXTRA_USER
import com.example.profilegithubsearcher.ui.favorite.FavoriteActivity
import com.example.profilegithubsearcher.ui.preferences.PreferencesActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val factory by lazy { ViewModelFactory.getInstance(this) }

    private val mainViewModel: MainViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.listUser.observe(this) {
            showRecycleView(it)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.isError.observe(this) { error ->
            if (error) errorHandler()
        }

        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }



        setUpSearchBar(binding)


    }

    private fun errorHandler() {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.pbLoading.visibility = android.view.View.VISIBLE
        } else {
            binding.pbLoading.visibility = android.view.View.GONE
        }
    }

    private fun setUpSearchBar(binding: ActivityMainBinding) {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchBar.setOnMenuItemClickListener { menuItemId ->
                when(menuItemId.itemId){
                    R.id.action_favorite -> {
                        val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.action_dark_mode -> {
                        val intent = Intent(this@MainActivity, PreferencesActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                searchBar.setText(searchView.text)
                searchView.hide()
                mainViewModel.searchUser(
                    searchView.text.toString()
                )
                false
            }
        }
    }

    private fun showRecycleView(user: ArrayList<User>) {
        val listUserAdapter = ListUserAdapter(user)
        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = listUserAdapter
            setHasFixedSize(true)
        }

        listUserAdapter.setOnItemClickCallBack(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val intent = Intent(this@MainActivity, DetailUserActivity::class.java)
                intent.putExtra(EXTRA_USER, data.login)
                startActivity(intent)
            }
        })
    }
}