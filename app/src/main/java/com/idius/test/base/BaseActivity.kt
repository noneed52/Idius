package com.idius.test.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.idius.test.R

abstract class BaseActivity<T : ViewDataBinding>: AppCompatActivity() {
    @get:LayoutRes
    abstract val layoutId: Int

    var viewDataBinding: T? = null
    private var loadingDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        viewDataBinding?.lifecycleOwner = this
    }

    /**
     * Replace current fragment with new fragment
     */
    fun replaceFragment(fragmentContainerId: Int, fragment: Fragment, fragmentTag: String) {
        supportFragmentManager.beginTransaction()
            .replace(fragmentContainerId, fragment, fragmentTag)
            .addToBackStack(fragmentTag)
            .commit()
    }

    /**
     * Show loading dialog
     */
    fun showLoading() {
        runOnUiThread {
            if(loadingDialog == null) {
                loadingDialog = Dialog(this).apply {
                    requestWindowFeature(Window.FEATURE_NO_TITLE)
                    window?.setBackgroundDrawableResource(android.R.color.transparent)
                    setCancelable(false)
                    setContentView(R.layout.dialog_loading)
                }
                if (!isFinishing)
                    loadingDialog?.show()
                else
                    loadingDialog = null
            }
        }
    }

    /**
     * Hide loading dialog
     */
    fun hideLoading() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }

    /**
     * Show toast message
     */
    fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Show toast message when there is error
     */
    fun errorHandler(throwable: Throwable) {
        toast(throwable.localizedMessage)
    }

    /**
     * Create viewmodel and return the viewmodel
     * @return ViewModel
     */
    inline fun <reified T : BaseViewModel> getViewModel(noinline creator: (() -> T)): T {
        return ViewModelProviders.of(this,
            BaseViewModelFactory(creator)
        ).get(T::class.java)
    }
}