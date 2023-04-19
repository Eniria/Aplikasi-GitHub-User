package com.example.githubuser

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.data.local.SettingPreferences
import com.example.githubuser.data.model.ResponsesUserGithub
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.detail.DetailActivity
import com.example.githubuser.favorite.FavoriteActivity


class MainActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityMainBinding
    private val adapter by lazy {
        UserAdapter{ user ->
            Intent(this, DetailActivity::class.java).apply {
                putExtra("item", user)
                startActivity(this)
            }
        }
    }
    private val viewModel by viewModels<MainViewModel>{
        MainViewModel.Factory(SettingPreferences(this))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getTheme().observe(this){
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.favorite ->{
                Intent(this, FavoriteActivity::class.java).apply {
                    startActivity(this)
                }
            }
//            R.id.setting -> {
//                Intent(this, SettingActivity::class.java).apply {
//                    startActivity(this)
//                }
//            }
        }
        return super.onOptionsItemSelected(item)
    }
}
