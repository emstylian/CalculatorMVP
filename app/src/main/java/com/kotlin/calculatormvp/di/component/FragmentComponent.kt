package com.kotlin.calculatormvp.di.component

import com.kotlin.calculatormvp.di.module.FragmentModule
import com.kotlin.calculatormvp.ui.calculatorScreen.CalculatorFragment
import dagger.Component


@Component(modules = arrayOf(FragmentModule::class))
interface FragmentComponent {
    fun inject(calculatorFragment: CalculatorFragment)

}