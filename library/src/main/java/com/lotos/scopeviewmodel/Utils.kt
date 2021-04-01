package com.lotos.scopeviewmodel

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner

internal fun LifecycleOwner.isChangingConfigurations(): Boolean {
    return when (this) {
        is Fragment -> activity != null && activity?.isChangingConfigurations ?: false
        is FragmentActivity -> isChangingConfigurations
        else -> false
    }
}

