package com.kotlin.calculatormvp.ui.main


class MainPresenter : MainContract.Presenter {

    private lateinit var view: MainContract.View

    /* implementation of MainContract.Presenter interface below */

    override fun attach(view: MainContract.View) {
        this.view = view
    }

}