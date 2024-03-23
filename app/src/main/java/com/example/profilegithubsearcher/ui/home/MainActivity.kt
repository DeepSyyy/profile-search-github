package com.example.profilegithubsearcher.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profilegithubsearcher.adapter.ListUserAdapter
import com.example.profilegithubsearcher.data.repsonse.User
import com.example.profilegithubsearcher.databinding.ActivityMainBinding
import com.example.profilegithubsearcher.ui.detail.DetailUserActivity

import com.example.profilegithubsearcher.ui.detail.DetailUserActivity.Companion.EXTRA_USER

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.listUser.observe(this){
            showRecycleView(it)
        }

        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }

        mainViewModel.isError.observe(this){ error ->
            if(error) errorHandler()
        }

        setUpSearchBar(binding)



    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun errorHandler(){
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.pbLoading.visibility = android.view.View.VISIBLE
        } else {
            binding.pbLoading.visibility = android.view.View.GONE
        }
    }

    private fun setUpSearchBar(binding: ActivityMainBinding){
        with(binding){
            searchView.setupWithSearchBar(searchBar)
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