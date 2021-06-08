package com.gachateam.covtect_19

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.gachateam.covtect_19.databinding.FragmentRegistrationBinding
import com.gachateam.covtect_19.databinding.FragmentSymptomBinding

class SymptomFragment : Fragment() {

    private var _binding: FragmentSymptomBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSymptomBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = SymptomFragmentArgs.fromBundle(arguments as Bundle).user

        activity?.title = resources.getString(R.string.registration)

        binding.btnContinue.setOnClickListener { view ->
            val toPersonalInformationFragment =
                SymptomFragmentDirections.actionSymptomFragmentToPersonalInformationFragment(user)
            view.findNavController().navigate(toPersonalInformationFragment)
        }

        binding.btnCancel.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_symptomFragment_to_registrationFragment)
        )
    }
}