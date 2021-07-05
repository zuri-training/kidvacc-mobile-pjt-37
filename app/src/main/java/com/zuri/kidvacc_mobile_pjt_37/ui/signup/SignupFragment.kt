package com.zuri.kidvacc_mobile_pjt_37.ui.signup

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.checkbox.MaterialCheckBox
import com.zuri.kidvacc_mobile_pjt_37.R
import com.zuri.kidvacc_mobile_pjt_37.databinding.FragmentSignupBinding
import com.zuri.kidvacc_mobile_pjt_37.ui.login.LoginFragment

class SignupFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val sharedPref = activity?.getSharedPreferences("com.zuri.kidvacc_mobile_pjt_37", Context.MODE_PRIVATE)
        val binding = FragmentSignupBinding.inflate(inflater)
        var checkboxRememberMe: MaterialCheckBox = binding.checkBoxRememberMe

        binding.btnSignUp.setOnClickListener {
            if (checkboxRememberMe.isChecked){
                sharedPref?.edit()?.putBoolean("Open SignUp Screen", false)?.apply()
            }
            sharedPref?.edit()?.putBoolean("Open OnBoarding Screen", false)?.apply()
            requireActivity().supportFragmentManager.beginTransaction().remove(this@SignupFragment).commit()
        }

        binding.logInTextView.setOnClickListener {
            openLogInFragment()
            requireActivity().supportFragmentManager.beginTransaction().remove(this@SignupFragment).commit()
        }

        return binding.root
    }

    private fun openLogInFragment(){
        val fm: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fm.beginTransaction()
        fragmentTransaction.replace(R.id.fullscreen_frameLayout, LoginFragment())
        fragmentTransaction.commit()
    }
}