package com.isaquesoft.petcollection.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isaquesoft.petcollection.data.model.Collection
import com.isaquesoft.petcollection.data.repository.CollectionRepository
import com.isaquesoft.petcollection.presentation.state.HomeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Created by Isaque Nogueira on 27/08/2024
 */
class HomeFragmentViewModel(
    private val collectionRepository: CollectionRepository,
) : ViewModel() {
    private val _state =
        MutableStateFlow<HomeState>(
            HomeState.Idle,
        )

    val state: StateFlow<HomeState> = _state

    fun getListCollection() {
        viewModelScope.launch {
            val listCollection = collectionRepository.getListCollection()
            _state.value = HomeState.ShowListCollection(listCollection)
        }
    }

    fun insertFirstListCollection() {
        viewModelScope.launch {
            val listCollection = collectionRepository.createListCollection()
            collectionRepository.insertListCollection(listCollection).run {
                getListCollection()
            }
        }
    }

    fun updateCollection(randomCollection: Collection) {
        viewModelScope.launch {
            collectionRepository.updateCollection(randomCollection)
        }
    }

    fun idle() {
        _state.value = HomeState.Idle
    }
}
