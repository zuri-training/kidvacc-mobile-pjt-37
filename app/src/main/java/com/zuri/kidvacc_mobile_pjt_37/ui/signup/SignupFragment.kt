package com.zuri.kidvacc_mobile_pjt_37.ui.signup

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.android.volley.Request
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.zuri.kidvacc_mobile_pjt_37.R
import com.zuri.kidvacc_mobile_pjt_37.databinding.FragmentSignupBinding
import com.zuri.kidvacc_mobile_pjt_37.networking.VolleyAuth
import com.zuri.kidvacc_mobile_pjt_37.networking.VolleySingleton
import com.zuri.kidvacc_mobile_pjt_37.ui.login.LoginFragment
import org.json.JSONObject

class SignupFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val sharedPref = activity?.getSharedPreferences("com.zuri.kidvacc_mobile_pjt_37", Context.MODE_PRIVATE)
        val binding = FragmentSignupBinding.inflate(inflater)
        var checkboxRememberMe: MaterialCheckBox = binding.checkBoxRememberMe

        binding.btnSignUp.setOnClickListener {

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

    private fun addTextListener(etTextInputEditText: TextInputEditText, etTextInputLayout: TextInputLayout){
        etTextInputEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                etTextInputLayout.error = null
            }
        })
    }

    private fun signUp(email: String, password: String,passwordConfirm: String, isChecked: Boolean, sharedPref: SharedPreferences?){
        val jsonBodyAuth = JSONObject()
        jsonBodyAuth.put("username", email)
        jsonBodyAuth.put("email", password)
        jsonBodyAuth.put("password2", password)
        jsonBodyAuth.put("password", password)

        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, VolleyAuth.URL_LOGIN, jsonBodyAuth,{ response ->
            if (isChecked) {
                sharedPref?.edit()?.putBoolean("Open SignUp Screen", false)?.apply()
            }
            sharedPref?.edit()?.putBoolean("Open OnBoarding Screen", false)?.apply()
            requireActivity().supportFragmentManager.beginTransaction().remove(this@SignupFragment).commit()
            VolleyLog.wtf(response.getString("key"))
        }) {error ->
            val code = error.networkResponse.statusCode
            error.printStackTrace()
        }
        VolleySingleton.getInstance(requireActivity()).addToRequestQueue(jsonObjectRequest)
    }

}