package com.isaquesoft.geradorderecibos.application

import android.app.Application
import com.isaquesoft.petcollection.framework.di.loadCollectionModule
import com.isaquesoft.sample.framework.di.loadAppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class AppAplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(listOf())
        }
        loadAppModule()
        loadCollectionModule()
    }
}
