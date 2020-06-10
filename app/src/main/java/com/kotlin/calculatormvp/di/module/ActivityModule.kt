package com.kotlin.calculatormvp.di.module

import android.app.Activity
import com.kotlin.calculatormvp.ui.main.MainContract
import com.kotlin.calculatormvp.ui.main.MainPresenter
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private var activity: Activity) {

    @Provides
    fun provideActivity(): Activity {
        return activity
    }

    @Provides
    fun provideMainPresenter(): MainContract.Presenter {
        return MainPresenter()
    }
}