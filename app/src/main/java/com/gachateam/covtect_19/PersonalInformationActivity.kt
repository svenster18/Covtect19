package com.gachateam.covtect_19

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gachateam.covtect_19.databinding.ActivityPersonalInformationBinding
import java.lang.StringBuilder
import kotlin.random.Random

class PersonalInformationActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_user"
    }

    private lateinit var binding: ActivityPersonalInformationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.personal_information)

        val user = intent.getParcelableExtra<User>(SymptomActivity.EXTRA_USER) as User

        user.userId = generateUserId()

        binding.tvUserIdHasil.text = user.userId.toString()
        binding.tvAgeHasil.text = user.age.toString()
        binding.tvGenderHasil.text = user.gender
        binding.tvHeightHasil.text = user.height.toString()
        binding.tvWeightHasil.text = user.weight.toString()

        binding.btnContinue.setOnClickListener {
            val intent = Intent(this@PersonalInformationActivity, RecordCoughActivity::class.java)
            intent.putExtra(RecordCoughActivity.EXTRA_USER, user)
            startActivity(intent)
        }
        binding.btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun generateUserId(): String {
        val stringBuilder = StringBuilder()

        for(i in 1..10) {
            stringBuilder.append(Random.nextInt(0,9))
        }
        return stringBuilder.toString()
    }
}