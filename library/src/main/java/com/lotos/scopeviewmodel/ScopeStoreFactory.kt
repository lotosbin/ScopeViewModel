package com.lotos.scopeviewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel

internal class ScopeStoreFactory : ScopeViewModelStore.Callback {

    private val storeMap = HashMap<String, HashMap<String, ScopeViewModelStore>>()

    private fun <T : ViewModel> get(name: String, clazz: Class<T>, owner: LifecycleOwner): ScopeViewModelStore? = storeMap.getOrPut(name, { HashMap() })[clazz.name]?.apply {
        addOwner(name, owner)
    }

    private fun <T : ViewModel> create(name: String, clazz: Class<T>, owner: LifecycleOwner): ScopeViewModelStore {
        return ScopeViewModelStore.create(name, clazz, this).apply {
            addOwner(name, owner)
            storeMap.getOrPut(name, { HashMap() })[clazz.name] = this
        }
    }

    fun <T : ViewModel> getOrCreate(name: String, clazz: Class<T>, owner: LifecycleOwner): ScopeViewModelStore = get(name, clazz, owner) ?: create(name, clazz, owner)

    override fun onStoreClear(name: String, clazz: Class<*>) {
        storeMap[name]?.remove(clazz.name)
    }
}