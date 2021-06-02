package com.gachateam.covtect_19

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gachateam.covtect_19.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.registration)

        binding.btnContinue.setOnClickListener {

            val user = registration()

            val intent = Intent(this@RegistrationActivity, SymptomActivity::class.java)
            intent.putExtra(SymptomActivity.EXTRA_USER, user)
            startActivity(intent)
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun registration(): User {
        val age = binding.edtAge.text.toString().toInt()
        val gender = if (binding.rbMale.isChecked) {
            "Laki-Laki"
        } else {
            "Perempuan"
        }
        val height = binding.edtHeight.text.toString().toInt()
        val weight = binding.edtWeight.text.toString().toInt()

        return User(null, age, gender, height, weight)
    }
}