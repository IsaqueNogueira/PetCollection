package com.isaquesoft.petcollection.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isaquesoft.petcollection.data.repository.CollectionRepository
import com.isaquesoft.petcollection.presentation.state.CollectionState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CollectionFragmentViewModel(
    private val collectionRepository: CollectionRepository,
) : ViewModel() {
    private val _state =
        MutableStateFlow<CollectionState>(
            CollectionState.Idle,
        )

    val state: StateFlow<CollectionState> = _state

    fun getListCollection() {
        viewModelScope.launch {
            val listCollection = collectionRepository.getListCollection()
            _state.value = CollectionState.ShowListCollection(listCollection)
        }
    }
}
