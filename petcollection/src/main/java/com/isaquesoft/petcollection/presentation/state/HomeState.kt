package com.isaquesoft.petcollection.presentation.state

import com.isaquesoft.petcollection.data.model.Collection

/**
 * Created by Isaque Nogueira on 27/08/2024
 */
sealed class HomeState {
    object Idle : HomeState()

    data class ShowListCollection(
        val listCollection: List<Collection>,
    ) : HomeState()
}
