package com.example.busstation.base

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
import com.example.myapplication.R

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
     * Manually show keyboard
     */
    fun showKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }

    /**
     * Manually hide keyboard
     */
    fun hideKeyboard() {
        viewDataBinding?.root?.run {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(windowToken, 0)
        }
    }

    /**
     * Show loading dialog
     */
    fun showLoading() {
        runOnUiThread {
            loadingDialog  = Dialog(this).apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                window?.setBackgroundDrawableResource(android.R.color.transparent)
                setCancelable(false)
                setContentView(R.layout.dialog_loading)
            }
            if(!isFinishing)
                loadingDialog?.show()
            else
                loadingDialog = null
        }
    }

    /**
     * Hide loading dialog
     */
    fun hideLoading() {
        loadingDialog?.dismiss()
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
    inline fun <reified T : com.example.busstation.base.BaseViewModel> getViewModel(noinline creator: (() -> T)): T {
        return ViewModelProviders.of(this, com.example.busstation.base.BaseViewModelFactory(creator)).get(T::class.java)
    }
}