package com.gachateam.covtect_19

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gachateam.covtect_19.databinding.ActivitySymptomBinding

class SymptomActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    private lateinit var binding: ActivitySymptomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySymptomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User

        supportActionBar?.title = resources.getString(R.string.registration)

        binding.btnContinue.setOnClickListener {
            val intent = Intent(this@SymptomActivity, PersonalInformationActivity::class.java)
            intent.putExtra(PersonalInformationActivity.EXTRA_USER, user)
            startActivity(intent)
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }
    }
}