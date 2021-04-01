package com.lotos.scopeviewmodel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

@PublishedApi
internal abstract class ScopeDestroyObserver(
    private val lifecycleOwner: LifecycleOwner
) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        if (!lifecycleOwner.isChangingConfigurations()) {
            onLifeCycleDestroy()
        }
    }

    abstract fun onLifeCycleDestroy()
}