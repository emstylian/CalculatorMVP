package com.kotlin.calculatormvp.di.module

import androidx.fragment.app.Fragment
import com.kotlin.calculatormvp.ui.calculatorScreen.CalculatorContract
import com.kotlin.calculatormvp.ui.calculatorScreen.CalculatorPresenter
import dagger.Module
import dagger.Provides


@Module
class FragmentModule(private var fragment: Fragment) {

    @Provides
    fun providesFragment(): Fragment {
        return fragment
    }

    @Provides
    fun provideCalculatorPresenter(): CalculatorContract.Presenter {
        return CalculatorPresenter()
    }

}