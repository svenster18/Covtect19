package com.gachateam.covtect_19

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.gachateam.covtect_19.databinding.FragmentHomeBinding
import com.gachateam.covtect_19.databinding.FragmentRegistrationBinding
import java.lang.NullPointerException
import java.lang.NumberFormatException

class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.title = resources.getString(R.string.registration)

        binding.btnContinue.setOnClickListener { view ->

            val user = registration()

            if (user != null) {
                val toSymptomFragment = RegistrationFragmentDirections.actionRegistrationFragmentToSymptomFragment(user)
                view.findNavController().navigate(toSymptomFragment)
            }
        }

        binding.btnCancel.setOnClickListener (
            Navigation.createNavigateOnClickListener(R.id.action_registrationFragment_to_agreementFragment)
        )
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
            return User(null, age, gender, height, weight, null)
        } catch (e: NumberFormatException) {
            binding.edtWeight.error = "Hanya diperbolehkan memasukkan Angka"
            return null
        }
    }
}