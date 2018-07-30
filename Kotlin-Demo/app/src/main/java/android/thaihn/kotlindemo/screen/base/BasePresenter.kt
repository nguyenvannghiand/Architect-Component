package com.thaihn.kotlinstart.screen.base

interface BasePresenter<V : BaseView> {

    fun onAttach(view: V)

    fun onDetach()
}