package com.gachateam.covtect_19

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.gachateam.covtect_19.databinding.FragmentPersonalInformationBinding
import com.gachateam.covtect_19.databinding.FragmentSymptomBinding
import java.lang.StringBuilder
import kotlin.random.Random

class PersonalInformationFragment : Fragment() {

    private var _binding: FragmentPersonalInformationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPersonalInformationBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.title = resources.getString(R.string.registration)

        val user = PersonalInformationFragmentArgs.fromBundle(arguments as Bundle).user

        user.userId = generateUserId()

        binding.tvUserIdHasil.text = user.userId.toString()
        binding.tvAgeHasil.text = user.age.toString()
        binding.tvGenderHasil.text = user.gender
        binding.tvHeightHasil.text = user.height.toString()
        binding.tvWeightHasil.text = user.weight.toString()

        binding.btnContinue.setOnClickListener {
            val toRecordCoughFragment =
                PersonalInformationFragmentDirections.actionPersonalInformationFragmentToRecordCoughFragment(user)
            view.findNavController().navigate(toRecordCoughFragment)
        }

        binding.btnCancel.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_personalInformationFragment_to_symptomFragment)
        )
    }

    private fun generateUserId(): String {
        val stringBuilder = StringBuilder()

        for(i in 1..10) {
            stringBuilder.append(Random.nextInt(0,9))
        }
        return stringBuilder.toString()
    }
}