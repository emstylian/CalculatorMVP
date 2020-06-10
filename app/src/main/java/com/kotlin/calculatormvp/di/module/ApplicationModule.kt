package com.kotlin.calculatormvp.di.module

import android.app.Application
import com.kotlin.calculatormvp.BaseApp
import com.kotlin.calculatormvp.di.scope.PerApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val baseApp: BaseApp) {

    @Provides
    @Singleton
    @PerApplication
    fun provideApplication(): Application {
        return baseApp
    }
}