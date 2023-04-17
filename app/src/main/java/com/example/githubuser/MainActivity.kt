package com.example.githubuser

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.data.model.ResponsesUserGithub
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.detail.DetailActivity


class MainActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityMainBinding
    private val adapter by lazy {
        UserAdapter{ user ->
            Intent(this, DetailActivity::class.java).apply {
                putExtra("username", user.login )
                startActivity(this)
            }
        }
    }
    private val viewModel by viewModels<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(pO: String?): Boolean {
                viewModel.getUser(pO.toString())
                return true
            }

            override fun onQueryTextChange(pO: String?): Boolean = false
        })

        viewModel.resultUser.observe(this){
            when(it){
                is com.example.githubuser.utils.Result.Success<*> -> {
                    adapter.setData(it.data as MutableList<ResponsesUserGithub.Item>)
                }
                is com.example.githubuser.utils.Result.Error -> {
                    Toast.makeText(this, it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is com.example.githubuser.utils.Result.Loading -> {
                    binding.progressBar.isVisible = it.isLoading
                }
            }
        }
        viewModel.getUser()

    }
}
