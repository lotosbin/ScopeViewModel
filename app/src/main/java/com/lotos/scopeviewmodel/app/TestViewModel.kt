package com.lotos.scopeviewmodel.app

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TestViewModel : ViewModel() {
    val message = MutableLiveData<String>()
}
