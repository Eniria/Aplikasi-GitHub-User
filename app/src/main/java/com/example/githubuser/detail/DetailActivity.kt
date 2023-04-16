package com.example.githubuser.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import coil.load
import coil.transform.CircleCropTransformation
import com.example.githubuser.data.model.ResponseDetailUser
import com.example.githubuser.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val username = intent.getStringExtra("username") ?: ""

        viewModel.resultDetailUser.observe(this){
            when(it){
                is com.example.githubuser.utils.Result.Success<*> -> {
                    val user =it.data as ResponseDetailUser
                    binding.image.load(user.avatar_url){
                        transformations(CircleCropTransformation())
                    }
                    binding.nama.text = user.name
                }
                is com.example.githubuser.utils.Result.Error -> {
                    Toast.makeText(this, it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is com.example.githubuser.utils.Result.Loading -> {
                    binding.progressBar.isVisible= it.isLoading
                }
            }
        }
        viewModel.getDetailUser(username)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}