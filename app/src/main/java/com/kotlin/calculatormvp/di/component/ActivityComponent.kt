package com.kotlin.calculatormvp.di.component

import com.kotlin.calculatormvp.di.module.ActivityModule
import com.kotlin.calculatormvp.ui.main.MainActivity
import dagger.Component

@Component(modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)
}