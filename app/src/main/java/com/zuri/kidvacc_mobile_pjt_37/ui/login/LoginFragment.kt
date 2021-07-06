package com.zuri.kidvacc_mobile_pjt_37.ui.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.zuri.kidvacc_mobile_pjt_37.R
import com.zuri.kidvacc_mobile_pjt_37.databinding.FragmentLoginBinding
import com.zuri.kidvacc_mobile_pjt_37.networking.VolleyAuth
import com.zuri.kidvacc_mobile_pjt_37.networking.VolleySingleton
import com.zuri.kidvacc_mobile_pjt_37.ui.signup.SignupFragment
import org.json.JSONObject
import java.util.*


class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val sharedPref = activity?.getSharedPreferences(
            "com.zuri.kidvacc_mobile_pjt_37",
            Context.MODE_PRIVATE
        )
        val binding = FragmentLoginBinding.inflate(inflater)
        val checkboxRememberMe: MaterialCheckBox = binding.checkBoxRememberMe
        //et_email
        val etEmailLayout: TextInputLayout = binding.etEmailLayout
        val emailTextView: TextInputEditText = binding.etEmail
        addTextListener(emailTextView,etEmailLayout)

        val etPasswordLayout: TextInputLayout = binding.etPasswordLayout
        val passwordTextView: TextInputEditText = binding.etPassword
        addTextListener(passwordTextView,etPasswordLayout)

        binding.btnLogin.setOnClickListener {
            val email = emailTextView.text.toString()
            val password = passwordTextView.text.toString()

            if (!(email.isBlank() || password.isEmpty())){
                logIn(
                    emailTextView.text.toString(),
                    passwordTextView.text.toString(),
                    checkboxRememberMe.isChecked,
                    sharedPref
                )
            }else{
                if (email.isBlank()){
                    etEmailLayout.error = "Email Field Can't Be Empty"
                }

                if (password.isEmpty()){
                    etPasswordLayout.error = "Password Field Can't Be Empty"
                }
            }
        }

        binding.createAnAccount.setOnClickListener {
            openSignUpWithEmailFragment()
            requireActivity().supportFragmentManager.beginTransaction().remove(this@LoginFragment)
                .commit()
        }
        return binding.root
    }

    private fun openSignUpWithEmailFragment() {
        val fm: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fm.beginTransaction()
        fragmentTransaction.replace(R.id.fullscreen_frameLayout, SignupFragment())
        fragmentTransaction.commit()
    }

    private fun addTextListener(etTextInputEditText: TextInputEditText,etTextInputLayout: TextInputLayout){
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

    private fun logIn(email: String, password: String, isChecked: Boolean, sharedPref: SharedPreferences?){
        val jsonBodyAuth = JSONObject()
        jsonBodyAuth.put("username", email)
        jsonBodyAuth.put("password", password)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            VolleyAuth.URL_LOGIN,
            jsonBodyAuth,
            { response ->
                val token = response.getString("key")
                VolleyAuth.TOKEN = token
                if (isChecked) {
                    sharedPref?.edit()?.putBoolean("Open SignUp Screen", false)?.apply()
                    sharedPref?.edit()?.putString("TOKEN", token)?.apply()
                }
                /*sharedPref?.edit()?.putBoolean("Open OnBoarding Screen", false)?.apply()
                requireActivity().supportFragmentManager.beginTransaction().remove(this@LoginFragment).commit()*/
            }) { error ->
            val code = error.networkResponse.statusCode
            val errorString = String(error.networkResponse.data)
            if (code == 400){
                Log.i("Error Code", "" + code)
                val jsonError = JSONObject(errorString)
                if (jsonError.getJSONArray("non_field_errors").get(0).toString().equals("Unable to log in with provided credentials.")){
                 Toast.makeText(requireActivity(),"Invalid Details",Toast.LENGTH_SHORT).show()
                }
            }
        }
        VolleySingleton.getInstance(requireActivity()).addToRequestQueue(jsonObjectRequest)
    }
}