package com.lotos.scopeviewmodel

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore

internal class ScopeViewModelStore private constructor(
    private val name: String,
    private val clazz: Class<*>,
    private val callback: Callback
) : ViewModelStore(), LifecycleObserver {
    private val owners = HashSet<String>()

    companion object {
        internal fun <T : ViewModel> create(name: String, clazz: Class<T>, callback: Callback): ScopeViewModelStore = ScopeViewModelStore(name, clazz, callback)
        private const val TAG = "ScopeViewModelStore"
    }

    private fun createDestroyObserver(owner: LifecycleOwner) =
        object : ScopeDestroyObserver(owner) {
            override fun onLifeCycleDestroy() {
                removeOwner(owner)
            }
        }

    private fun getOwnerKey(owner: LifecycleOwner) = owner.toString()

    fun addOwner(name: String, owner: LifecycleOwner) {
        if (owners.contains(getOwnerKey(owner))) {
            Log.d(TAG, "scope $name $owner is already added")
            return
        }
        owners.add(getOwnerKey(owner))
        owner.apply {
            lifecycle.addObserver(createDestroyObserver(this))
        }
        Log.d(TAG, "scope $name $owner registered in $this as owner of ${clazz.simpleName} ")
    }


    private fun removeOwner(owner: LifecycleOwner) {
        owners.remove(getOwnerKey(owner))
        Log.d(TAG, "scope $name $owner unregistered from $this as ${clazz.simpleName}'s owner")
        if (owners.isEmpty()) {
            //Clear store when last owner is dead
            clear()
            callback.onStoreClear(name, clazz)
            Log.d(TAG, "scope $name $this, store of ${clazz.simpleName} cleared")
        }

    }

    interface Callback {
        fun onStoreClear(name: String, clazz: Class<*>)
    }
}