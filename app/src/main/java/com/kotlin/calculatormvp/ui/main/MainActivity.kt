package com.kotlin.calculatormvp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.kotlin.calculatormvp.R
import com.kotlin.calculatormvp.di.component.DaggerActivityComponent
import com.kotlin.calculatormvp.di.module.ActivityModule
import com.kotlin.calculatormvp.ui.calculatorScreen.CalculatorFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigDecimal
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainContract.View {

/* for navigation between fragments */
private enum class CURRENT_FRAGMENT { CALCULATOR_FRAGMENT }
private var fragment_enabled = CURRENT_FRAGMENT.CALCULATOR_FRAGMENT

@Inject
lateinit var presenter: MainContract.Presenter

private var calculatorFragment: CalculatorFragment? = null



override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    supportActionBar?.hide()
    injectDependency()
    showCalculatorFragment()
}

private fun injectDependency() {
    val activityComponent = DaggerActivityComponent.builder()
        .activityModule(ActivityModule(this))
        .build()
    activityComponent.inject(this)
}

/* implementation of MainContract.View interface below */
override fun showCalculatorFragment() {
    if (calculatorFragment == null) {
        calculatorFragment = CalculatorFragment.newInstance()
    }
    supportFragmentManager.beginTransaction()
        .replace(
            R.id.mainFragmentFL,
            calculatorFragment!!,
            CalculatorFragment.TAG
        )
        .commit()
    fragment_enabled = CURRENT_FRAGMENT.CALCULATOR_FRAGMENT
}


override fun showSnack(msg: String) {
    val snack = Snackbar.make(mainFragmentFL, msg, Snackbar.LENGTH_LONG)
    snack.show()
}



override fun onBackPressed() {
    if (fragment_enabled == CURRENT_FRAGMENT.CALCULATOR_FRAGMENT)
        super.onBackPressed()

    else
        super.onBackPressed()
}
}
