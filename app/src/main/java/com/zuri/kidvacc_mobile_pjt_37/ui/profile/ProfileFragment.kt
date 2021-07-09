package com.zuri.kidvacc_mobile_pjt_37.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.card.MaterialCardView
import com.zuri.kidvacc_mobile_pjt_37.R
import com.zuri.kidvacc_mobile_pjt_37.ui.add_a_child.Add_A_Child
import com.zuri.kidvacc_mobile_pjt_37.ui.settings.SettingsFragment

class ProfileFragment : Fragment() {
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        root.findViewById<MaterialCardView>(R.id.settings).setOnClickListener {
            val fm: FragmentManager? = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fm!!.beginTransaction()
            fragmentTransaction.replace(R.id.home_frameLayout, SettingsFragment())
            fragmentTransaction.commit()
        }

        root.findViewById<Button>(R.id.add_a_child).setOnClickListener {
            val fm: FragmentManager? = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fm!!.beginTransaction()
            fragmentTransaction.replace(R.id.home_frameLayout, Add_A_Child())
            fragmentTransaction.commit()
        }
        return root
    }
}