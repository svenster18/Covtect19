package com.gachateam.covtect_19

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.gachateam.covtect_19.databinding.FragmentRecordBreathBinding
import com.gachateam.covtect_19.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.title = getString(R.string.diagnose_result)

        binding.btnStart.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_resultFragment_to_agreementFragment)
        )

        binding.btnBack.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_resultFragment_to_homeFragment)
        )
    }
}