package com.gachateam.covtect_19

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.gachateam.covtect_19.databinding.FragmentAgreementBinding

class AgreementFragment : Fragment() {

    private var _binding: FragmentAgreementBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAgreementBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.title = resources.getString(R.string.user_agreement)

        binding.btnAgree.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_agreementFragment_to_registrationFragment)
        )

        binding.btnCancel.setOnClickListener (
            Navigation.createNavigateOnClickListener(R.id.action_agreementFragment_to_homeFragment)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}