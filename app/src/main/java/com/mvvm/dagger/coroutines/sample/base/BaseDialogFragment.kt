package com.mvvm.dagger.coroutines.sample.base

import android.app.Dialog
import android.content.Context
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.mvvm.dagger.coroutines.sample.R
import dagger.android.support.AndroidSupportInjection
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import javax.inject.Inject

abstract class BaseDialogFragment<VM: BaseViewModel,DB: ViewDataBinding>: DialogFragment(), IBaseView {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var fragmentContext: Context

    protected lateinit var dataBinding: DB
    protected lateinit var viewModel: VM

    abstract fun getLayoutId() : Int
    abstract fun getViewModelClass(): Class<VM>
    abstract fun getVariablesToBind(): Map<Int,Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        initViewModel()
        initView()
        return super.onCreateDialog(savedInstanceState)
    }

    open fun initViewModel() {
        viewModel = obtainViewModel(getViewModelClass())
    }

    open fun initView() {
        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(getViewContext()), getLayoutId(), null,false)
        dataBinding.lifecycleOwner = this
        for ((variableId,value) in getVariablesToBind()) {
            dataBinding.setVariable(variableId,value)
        }
        dataBinding.executePendingBindings()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context
    }

    override fun isActive(): Boolean = isAdded

    override fun showAlert(textResource: Int) {
        activity?.let {
            if (isActive() && !it.isFinishing) {
                it.alert(textResource) {
                    yesButton { dialog -> dialog.dismiss() }
                }.show()
            }
        }
    }

    override fun getViewContext(): Context = fragmentContext

    override fun <T : BaseViewModel> obtainViewModel(clazz: Class<T>): T = ViewModelProviders.of(this,viewModelFactory).get(clazz)

    override fun onNetworkError() = showAlert(R.string.error_network)
}