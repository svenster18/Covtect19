package com.gachateam.covtect_19

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gachateam.covtect_19.databinding.ActivityRegistrationBinding
import java.lang.NullPointerException
import java.lang.NumberFormatException

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.registration)

        binding.btnContinue.setOnClickListener {

            val user = registration()

            if (user != null) {
                val intent = Intent(this@RegistrationActivity, SymptomActivity::class.java)
                intent.putExtra(SymptomActivity.EXTRA_USER, user)
                startActivity(intent)
            }
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun registration(): User? {
        var age = 0
        var height = 0
        var weight = 0

        try {
            age = binding.edtAge.text.toString().toInt()
        } catch (e: NullPointerException) {
            binding.edtAge.error = "Wajib Diisi"
        } catch (e: NumberFormatException) {
            binding.edtAge.error = "Hanya diperbolehkan memasukkan Angka"
            return null
        }

        val gender = if (binding.rbMale.isChecked) {
            "Laki-Laki"
        } else {
            "Perempuan"
        }

        try {
            height = binding.edtHeight.text.toString().toInt()
        } catch (e: NumberFormatException) {
            binding.edtHeight.error = "Hanya diperbolehkan memasukkan Angka"
            return null
        }

        try {
            weight = binding.edtWeight.text.toString().toInt()
            return User(null, age, gender, height, weight)
        } catch (e: NumberFormatException) {
            binding.edtWeight.error = "Hanya diperbolehkan memasukkan Angka"
            return null
        }
    }
}