package com.lotos.scopeviewmodel

import android.app.Application
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

typealias FactoryFun<T> = () -> T

class ScopeViewModel internal constructor(@PublishedApi internal val app: Application) {
    private val viewModelStoreFactory = ScopeStoreFactory()

    @PublishedApi
    internal fun <T : ViewModel> createScopeProvider(name: String, clazz: Class<T>, lifecycleOwner: LifecycleOwner, factory: ViewModelProvider.Factory? = null): ViewModelProvider {
        val store = viewModelStoreFactory.getOrCreate(name, clazz, lifecycleOwner)
        return ViewModelProvider(store, factory ?: ViewModelProvider.AndroidViewModelFactory.getInstance(app))
    }

    companion object {
        lateinit var instance: ScopeViewModel
        fun init(application: Application) {
            instance = ScopeViewModel(application)
        }
    }
}

inline fun <reified T : ViewModel> FragmentActivity.getViewModel(scopeName: String, noinline factoryFun: FactoryFun<T>? = null): T {
    val factory = factoryFun?.let {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>) = it() as T
        }
    }
    return ScopeViewModel.instance.createScopeProvider(scopeName, T::class.java, this, factory)[T::class.java]
}
