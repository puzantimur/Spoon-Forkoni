package com.example.homew3.cleanArch.presentation

import android.app.Application
import com.example.homew3.cleanArch.data.di.dataModule
import com.example.homew3.cleanArch.presentation.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(
                viewModelModule,
                dataModule
            )
        }
    }
}
