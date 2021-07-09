package com.zuri.kidvacc_mobile_pjt_37.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import com.zuri.kidvacc_mobile_pjt_37.R

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        root.findViewById<AppCompatImageView>(R.id.iv_back).setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction().remove(this@SettingsFragment).commit();

        }
        // Inflate the layout for this fragment
        return root
    }
}