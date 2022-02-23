package com.reddit.rickandmortyapp.domain

import com.reddit.rickandmortyapp.network.RickAndMortyApi
import com.reddit.rickandmortyapp.network.RickAndMortyCharacter
import com.reddit.rickandmortyapp.network.RickAndMortyService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

class CharacterRepository(
    private val api: RickAndMortyApi =  RickAndMortyService().api,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    val stream = MutableStateFlow<Result<List<RickAndMortyCharacter>>>(Result.Loading)

    suspend fun fetchCharacters() {
        withContext(coroutineDispatcher) {
            stream.emit(Result.Loading)
            try {
                val result = api.fetchCharactersCoroutine()

                stream.emit(Result.Success(data = result.results))
            } catch (t: Throwable) {
                stream.emit(Result.Error(throwable = t))
            }
        }
    }
}