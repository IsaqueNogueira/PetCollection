package com.isaquesoft.petcollection.framework.di

import com.isaquesoft.petcollection.data.database.CollectionDatabase
import com.isaquesoft.petcollection.data.repository.CollectionRepository
import com.isaquesoft.petcollection.presentation.viewmodel.CollectionFragmentViewModel
import com.isaquesoft.petcollection.presentation.viewmodel.HomeFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module

/**
 * Created by Isaque Nogueira on 27/08/2024
 */

private val collectionModule =
    module {

        factory {
            CollectionDatabase.getDatabase(get()).collectionDao()
        }

        factory { CollectionRepository(collectionDao = get()) }

        viewModel {
            HomeFragmentViewModel(collectionRepository = get())
        }
        viewModel {
            CollectionFragmentViewModel(collectionRepository = get())
        }
    }

fun loadCollectionModule() {
    loadKoinModules(collectionModule)
}

fun unloadCollectionModule() {
    unloadKoinModules(collectionModule)
}
