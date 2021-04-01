package com.lotos.scopeviewmodel.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lotos.scopeviewmodel.app.databinding.ActivityTest1Binding
import com.lotos.scopeviewmodel.getViewModel

class Test1Activity : AppCompatActivity() {
    private val viewModel by lazy {
        getViewModel<TestViewModel>(intent.getStringExtra("name") ?: "")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTest1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.message.observe(this, {
            binding.textView.text = it
        })
        binding.button2.setOnClickListener {
            startActivity(Intent(this, Test2Activity::class.java).apply {
                putExtra("name", intent.getStringExtra("name") ?: "")
            })
        }
        binding.buttonSet.setOnClickListener {
            viewModel.message.postValue("${intent.getStringExtra("name")}1")
        }
    }
}