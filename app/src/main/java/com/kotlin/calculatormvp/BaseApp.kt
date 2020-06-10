package com.kotlin.calculatormvp

import android.app.Application
import com.kotlin.calculatormvp.di.component.ApplicationComponent
import com.kotlin.calculatormvp.di.component.DaggerApplicationComponent
import com.kotlin.calculatormvp.di.module.ApplicationModule


class BaseApp : Application() {

    lateinit var component: ApplicationComponent
    override fun onCreate() {
        super.onCreate()
        instance = this
        injectDependency()
    }

    fun injectDependency() {
        component = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
        component.inject(this)
    }

    companion object {
        lateinit var instance: BaseApp private set
    }
}