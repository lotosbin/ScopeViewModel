# ScopeViewModel
share viewModel between activities with same key

[![](https://jitpack.io/v/lotosbin/ScopeViewModel.svg)](https://jitpack.io/#lotosbin/ScopeViewModel)

# Usage
```kotlin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ScopeViewModel.init(this)
    }
}

class Test1Activity : AppCompatActivity() {
    private val viewModel by lazy {
        getViewModel<TestViewModel>(intent.getStringExtra("name") ?: "")
    }
    //...
}
class Test2Activity : AppCompatActivity() {
    private val viewModel by lazy {
        getViewModel<TestViewModel>(intent.getStringExtra("name") ?: "")
    }
    //...
}

```
Test1Activity & Test2Activity instance's share same viewmodel when there intent has same "name" extra value
