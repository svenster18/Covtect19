package com.gachateam.covtect_19

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.HandlerThread
import android.view.View
import com.gachateam.covtect_19.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvLoading.text = resources.getString(R.string.diagnose_result)
        binding.tvResult.visibility = View.VISIBLE
        binding.btnBack.visibility = View.VISIBLE
        binding.btnStart.visibility = View.VISIBLE
    }
}