package com.kotlin.calculatormvp.di.component

import com.kotlin.calculatormvp.BaseApp
import com.kotlin.calculatormvp.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {
    fun inject(application: BaseApp)
}