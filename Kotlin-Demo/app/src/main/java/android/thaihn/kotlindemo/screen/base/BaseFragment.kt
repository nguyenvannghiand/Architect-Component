package com.thaihn.kotlinstart.screen.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    protected abstract fun getLayoutResource(): Int

    protected abstract fun initVariable(saveInstanceState: Bundle?, rootView: View)

    protected abstract fun initData(saveInstanceState: Bundle?)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(getLayoutResource(), container, false)
        initVariable(savedInstanceState, rootView)
        initData(savedInstanceState)
        return rootView
    }
}
