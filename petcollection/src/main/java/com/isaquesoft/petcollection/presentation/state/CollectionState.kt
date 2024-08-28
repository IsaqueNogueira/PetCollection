package com.isaquesoft.petcollection.presentation.state

import com.isaquesoft.petcollection.data.model.Collection

/**
 * Created by Isaque Nogueira on 27/08/2024
 */
sealed class CollectionState {
    object Idle : CollectionState()

    data class ShowListCollection(
        val listCollection: List<Collection>,
    ) : CollectionState()
}
