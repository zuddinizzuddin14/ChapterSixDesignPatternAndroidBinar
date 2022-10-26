package com.example.pixabayapp.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pixabayapp.R
import com.example.pixabayapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels()

    private val adapter: PostAdapter by lazy {
        PostAdapter {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initList()
        observeData()
        setClickListener()
    }

    private fun setClickListener() {
    }

    private fun observeData() {
        viewModel.loadingState.observe(this) { isLoading ->
            binding.pbPost.isVisible = isLoading
            binding.rvPost.isVisible = !isLoading
        }
        viewModel.errorState.observe(this) { errorData ->
            binding.tvError.isVisible = errorData.first
            errorData.second?.message?.let {
                binding.tvError.text = it
            }
        }
        viewModel.searchResult.observe(this) {
            if (it.payload!!.posts.isEmpty()) {
                adapter.clearItems()
                binding.tvError.isVisible = true
                binding.tvError.text = getString(R.string.text_empty_state)
            } else {
                adapter.setItems(it.payload.posts)
            }
        }

    }

    private fun initList() {
        binding.rvPost.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)

        val search = menu.findItem(R.id.menu_search_bar)
        val searchView = search.actionView as androidx.appcompat.widget.SearchView
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchPost(it)
                }
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

}