package thaihn.com.weatherapp.screen.base.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import thaihn.com.weatherapp.screen.base.viewmodel.BaseViewModel

abstract class BaseBindingActivity<V : ViewDataBinding, M : BaseViewModel>()
    : BaseActivity() {

    var viewDataBinding: V? = null

    abstract val bindingVariable: Int

    abstract val viewModel: M

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, layoutResource)
        viewDataBinding?.apply {
            setVariable(bindingVariable, viewModel)
            executePendingBindings()
            setLifecycleOwner(this@BaseBindingActivity)
        }
        initVariable(savedInstanceState)
        initData(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.activityDestroyed()
    }
}