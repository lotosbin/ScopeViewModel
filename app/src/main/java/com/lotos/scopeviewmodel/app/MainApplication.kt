package com.lotos.scopeviewmodel.app

import android.app.Application
import com.lotos.scopeviewmodel.ScopeViewModel

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ScopeViewModel.init(this)
    }
}