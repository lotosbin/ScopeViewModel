package com.lotos.scopeviewmodel.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lotos.scopeviewmodel.app.databinding.ActivityTest2Binding
import com.lotos.scopeviewmodel.getViewModel

class Test2Activity : AppCompatActivity() {
    private val viewModel by lazy {
        getViewModel<TestViewModel>(intent.getStringExtra("name") ?: "")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTest2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.message.observe(this, {
            binding.textView2.text = it
        })
        binding.button.setOnClickListener {
            startActivity(Intent(this, Test1Activity::class.java).apply {
                putExtra("name", "2")
            })
        }
        binding.buttonSet.setOnClickListener {
            viewModel.message.postValue("${intent.getStringExtra("name")}2")
        }
    }
}