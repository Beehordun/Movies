package com.example.biodun.mdb

import android.app.Application
import com.example.biodun.mdb.di.AppComponent
import com.example.biodun.mdb.di.AppModule
import com.example.biodun.mdb.di.DaggerAppComponent

class MdbApplication : Application() {

    val appComponent: AppComponent
        get() = staticAppComponent

    private val appModule: AppModule
        get() = AppModule()

    override fun onCreate() {
        super.onCreate()
        initComponent()
    }

    private fun initComponent() {
        staticAppComponent = DaggerAppComponent.builder()
                .application(this)
                .appModule(appModule)
                .build()

        staticAppComponent.inject(this)
    }

    companion object {
        @JvmStatic lateinit var staticAppComponent: AppComponent
    }
}
