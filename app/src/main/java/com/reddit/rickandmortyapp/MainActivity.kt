package com.reddit.rickandmortyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.reddit.rickandmortyapp.databinding.ActivityMainBinding
import com.reddit.rickandmortyapp.ui.MainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.mainViewModelUiStateLiveData.observe(this) { uiState ->
            setVisibilityStates(uiState)
            when(uiState) {
                MainViewModel.MainViewModelUiState.Error -> {}
                MainViewModel.MainViewModelUiState.Loading -> {}
                is MainViewModel.MainViewModelUiState.Success -> {
                    setupRecycleView(uiState = uiState)
                }
            }
        }
    }

    private fun setupRecycleView(uiState: MainViewModel.MainViewModelUiState.Success) {
        val characterAdapter = CharacterAdapter(list = uiState.items)
        val linerLayoutManager = LinearLayoutManager(this)

        with(binding.recyclerView) {
            layoutManager = linerLayoutManager
            adapter = characterAdapter
        }
    }

    private fun setupView() {
        binding.btnTryAgain.setOnClickListener {
            viewModel.loadData()
        }
    }

    private fun setVisibilityStates(uiState: MainViewModel.MainViewModelUiState) {
        binding.progressBar.visibility = if (uiState is MainViewModel.MainViewModelUiState.Loading) View.VISIBLE else View.GONE
        binding.layoutError.visibility = if (uiState is MainViewModel.MainViewModelUiState.Error) View.VISIBLE else View.GONE
        binding.recyclerView.visibility = if (uiState is MainViewModel.MainViewModelUiState.Success) View.VISIBLE else View.GONE
    }
}