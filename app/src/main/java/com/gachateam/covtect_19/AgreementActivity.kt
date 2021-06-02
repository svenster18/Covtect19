package com.gachateam.covtect_19

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gachateam.covtect_19.databinding.ActivityAgreementBinding

class AgreementActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAgreementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgreementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.user_agreement)

        binding.btnAgree.setOnClickListener {
            val intent = Intent(this@AgreementActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }
    }
}