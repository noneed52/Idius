package com.idius.test.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<T: ViewDataBinding>: Fragment() {
    @get:LayoutRes
    abstract val layoutId: Int

    var viewDataBinding: T? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        viewDataBinding?.lifecycleOwner = this
        return viewDataBinding?.root
    }

    /**
     * Get activity of the fragment is attached to
     */
    fun getAttachedActivity(): BaseActivity<*>? {
        return activity?.let {
            it as BaseActivity<*>
        }
    }
}