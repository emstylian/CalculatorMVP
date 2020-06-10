package com.kotlin.calculatormvp.ui.base

class BaseContract {
    interface Presenter<in T> {
        fun attach(view: T)
    }

    interface View {
        fun showSnack(msg: String)
    }
}