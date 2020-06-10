package com.kotlin.calculatormvp.ui.main

import com.kotlin.calculatormvp.ui.base.BaseContract
import java.math.BigDecimal

class MainContract {
    interface View : BaseContract.View {
        fun showCalculatorFragment()

    }
    interface Presenter : BaseContract.Presenter<View>
}