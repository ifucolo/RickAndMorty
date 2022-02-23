package com.reddit.rickandmortyapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.reddit.rickandmortyapp.domain.CharacterRepository
import com.reddit.rickandmortyapp.domain.Result
import com.reddit.rickandmortyapp.network.RickAndMortyCharacter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: CharacterRepository = CharacterRepository()
): ViewModel() {

    init {
        loadData()
    }

    val mainViewModelUiStateLiveData: LiveData<MainViewModelUiState> = liveData {
        repository.stream.collect { result ->
            when(result) {
                is Result.Error -> emit(MainViewModelUiState.Error)
                Result.Loading -> emit(MainViewModelUiState.Loading)
                is Result.Success -> emit(MainViewModelUiState.Success(items = result.data))
            }
        }
    }

    fun loadData() {
        viewModelScope.launch {
            repository.fetchCharacters()
        }
    }

    sealed class MainViewModelUiState {
        object Loading: MainViewModelUiState()
        object Error: MainViewModelUiState()
        data class Success(val items: List<RickAndMortyCharacter>): MainViewModelUiState()
    }
}