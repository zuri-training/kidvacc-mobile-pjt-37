package com.zuri.kidvacc_mobile_pjt_37.ui.hospitals

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.zuri.kidvacc_mobile_pjt_37.R
import com.zuri.kidvacc_mobile_pjt_37.databinding.FragmentHospitalsBinding

class HospitalsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentHospitalsBinding.inflate(inflater)

        binding.apply {
            val navController = findNavController()
            tvPrivate.setOnClickListener {
                tvPrivate.setTypeface(null, Typeface.BOLD)
                tvPublic.setTypeface(null, Typeface.NORMAL)
                navController.navigate(R.id.privateHospitalsFragment)
            }
            tvPublic.setOnClickListener {
                tvPublic.setTypeface(null, Typeface.BOLD)
                tvPrivate.setTypeface(null, Typeface.NORMAL)
                navController.navigate(R.id.publicHospitalsFragment)
            }
        }

        return binding.root
    }
}