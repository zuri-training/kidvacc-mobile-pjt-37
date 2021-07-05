package com.zuri.kidvacc_mobile_pjt_37.ui.signup

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.zuri.kidvacc_mobile_pjt_37.R
import com.zuri.kidvacc_mobile_pjt_37.databinding.SignUpPageFragmentBinding
import com.zuri.kidvacc_mobile_pjt_37.ui.login.LoginFragment

class SignUpPage : Fragment() {

    companion object {
        fun newInstance() = SignUpPage()
    }

    private lateinit var viewModel: SignUpPageViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //viewModel = ViewModelProvider(this).get(SignUpPageViewModel::class.java)
        val sharedPref = activity?.getSharedPreferences("com.zuri.kidvacc_mobile_pjt_37", Context.MODE_PRIVATE)
        val binding = SignUpPageFragmentBinding.inflate(inflater)

        binding.logInTextView.setOnClickListener {
            openLogInFragment();
            requireActivity().supportFragmentManager.beginTransaction().remove(this@SignUpPage).commit()
        }

        binding.btnSignUp.setOnClickListener {
            openSignUpWithEmailFragment();
            requireActivity().supportFragmentManager.beginTransaction().remove(this@SignUpPage).commit()
        }

        return binding.root
    }

    private fun openLogInFragment(){
        val fm: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fm.beginTransaction()
        fragmentTransaction.replace(R.id.fullscreen_frameLayout, LoginFragment())
        fragmentTransaction.commit()
    }

    private fun openSignUpWithEmailFragment(){
        val fm: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fm.beginTransaction()
        fragmentTransaction.replace(R.id.fullscreen_frameLayout, SignupFragment())
        fragmentTransaction.commit()
    }

}