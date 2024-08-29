package com.isaquesoft.sample.framework.di

import com.isaquesoft.petcollection.domain.PetCollectionStarter
import com.isaquesoft.petcollection.presentation.stater.PetCollectionStarterImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.loadKoinModules
import org.koin.core.context.GlobalContext.unloadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Created by Isaque Nogueira on 24/08/2024
 */
private val sampleModule: Module =

    module {
        factory<PetCollectionStarter> { PetCollectionStarterImpl(context = androidContext()) }
    }

fun loadAppModule() {
    loadKoinModules(sampleModule)
}

fun unloadAppModule() {
    unloadKoinModules(sampleModule)
}
