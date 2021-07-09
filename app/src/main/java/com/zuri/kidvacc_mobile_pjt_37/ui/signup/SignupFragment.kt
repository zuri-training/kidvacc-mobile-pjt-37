package com.zuri.kidvacc_mobile_pjt_37.ui.signup

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.zuri.kidvacc_mobile_pjt_37.databinding.FragmentSignupBinding
import com.zuri.kidvacc_mobile_pjt_37.networking.VolleyAuth
import com.zuri.kidvacc_mobile_pjt_37.networking.VolleySingleton
import com.zuri.kidvacc_mobile_pjt_37.ui.login.LoginFragment
import org.json.JSONObject


class SignupFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val sharedPref = activity?.getSharedPreferences(
            "com.zuri.kidvacc_mobile_pjt_37",
            Context.MODE_PRIVATE
        )
        val binding = FragmentSignupBinding.inflate(inflater)
        var checkboxRememberMe: MaterialCheckBox = binding.checkBoxRememberMe

        val etUsername: TextInputLayout = binding.etFullNameLayout
        val usernameTextView:TextInputEditText = binding.etFullName
        addTextListener(usernameTextView, etUsername)

        val etEmailLayout: TextInputLayout = binding.etEmailLayout
        val emailTextView: TextInputEditText = binding.etEmail
        addTextListener(emailTextView, etEmailLayout)

        val etPasswordLayout: TextInputLayout = binding.etPasswordLayout
        val passwordTextView: TextInputEditText = binding.etPassword
        addTextListener(passwordTextView, etPasswordLayout)

        val etConfirmPasswordLayout: TextInputLayout = binding.etConfirmPasswordLayout
        val confirmPasswordTextView: TextInputEditText = binding.etConfirmPassword
        addTextListener(confirmPasswordTextView, etConfirmPasswordLayout)

        binding.btnSignUp.setOnClickListener {
            val email = emailTextView.text.toString()
            val username = usernameTextView.text.toString()
            val password = passwordTextView.text.toString()
            val password2 = confirmPasswordTextView.text.toString()

            if (!(username.isBlank() || password.isEmpty() || password2.isEmpty())){
                val strength: String = calculateStrength(password)

                if (strength.equals("Weak")) {
                    etPasswordLayout.error = "Password Is Too Weak"
                }
                else if (strength.equals("Medium")) {
                   if (password.equals(password2)){
                       signUp(
                           email,
                           username,
                           password,
                           password2,
                           checkboxRememberMe.isChecked,
                           sharedPref
                       )
                   }else{
                       etConfirmPasswordLayout.error = "Passwords Do Not Match"
                   }
                }
                else if (strength.equals("Strong")) {
                    if (password.equals(password2)){
                        signUp(
                            email,
                            username,
                            password,
                            password2,
                            checkboxRememberMe.isChecked,
                            sharedPref
                        )
                    }else{
                        etConfirmPasswordLayout.error = "Passwords Do Not Match"
                    }
                }
                else {
                    if (password.equals(password2)){
                        signUp(
                            email,
                            username,
                            password,
                            password2,
                            checkboxRememberMe.isChecked,
                            sharedPref
                        )
                    }else{
                        etConfirmPasswordLayout.error = "Passwords Do Not Match"
                    }
                }

            }else{
                if (username.isBlank()){
                    etUsername.error = "Username Field Can't Be Empty"
                }

                if (password.isEmpty()){
                    etPasswordLayout.error = "Password Field Can't Be Empty"
                }

                if (password2.isEmpty()){
                    etConfirmPasswordLayout.error = "Password Field Can't Be Empty"
                }
            }
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

    private fun addTextListener(
        etTextInputEditText: TextInputEditText, etTextInputLayout: TextInputLayout
    ){
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

    private fun signUp(
        email: String,
        username: String,
        password: String,
        passwordConfirm: String,
        isChecked: Boolean,
        sharedPref: SharedPreferences?
    ){
        val jsonBodyAuth = JSONObject()
        jsonBodyAuth.put("username", username)
        jsonBodyAuth.put("email", email)
        jsonBodyAuth.put("password1", password)
        jsonBodyAuth.put("password2", passwordConfirm)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            VolleyAuth.URL_REGISTER,
            jsonBodyAuth,
            { response ->
                //VolleyLog.wtf(response.getString("key"))
                val token = response.getString("key")
                VolleyAuth.TOKEN = token
                if (isChecked) {
                    sharedPref?.edit()?.putBoolean("Open SignUp Screen", false)?.apply()
                    sharedPref?.edit()?.putString("TOKEN", token)?.apply()
                } else {
                    sharedPref?.edit()?.putString("TOKEN", "")?.apply()
                }
                sharedPref?.edit()?.putBoolean("Open OnBoarding Screen", false)?.apply()
                requireActivity().supportFragmentManager.beginTransaction()
                    .remove(this@SignupFragment).commit()
            }) { error ->
            try {
                val code = error.networkResponse.statusCode
                val errorString = String(error.networkResponse.data)
                if (code == 400){
                    val jsonError = JSONObject(errorString)
                    if (jsonError.getJSONArray("non_field_errors").get(0).toString().equals("Unable to log in with provided credentials.")){
                        Toast.makeText(requireActivity(), "Invalid Details", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(requireActivity(), "An Error Occurred", Toast.LENGTH_SHORT).show()
                    }
                }
            }catch (exception: Exception){
                Toast.makeText(requireActivity(), "An Error Occurred", Toast.LENGTH_SHORT).show()
            }
            error.printStackTrace()
        }
        VolleySingleton.getInstance(requireActivity()).addToRequestQueue(jsonObjectRequest)
    }

    var REQUIRED_LENGTH = 8
    var MAXIMUM_LENGTH = 15
    var REQUIRE_SPECIAL_CHARACTERS = true
    var REQUIRE_DIGITS = true
    var REQUIRE_LOWER_CASE = true
    var REQUIRE_UPPER_CASE = false

    fun calculateStrength(password: String): String {
        var currentScore = 0
        var sawUpper = false
        var sawLower = false
        var sawDigit = false
        var sawSpecial = false
        for (element in password) {
            val c = element
            if (!sawSpecial && !Character.isLetterOrDigit(c)) {
                currentScore += 1
                sawSpecial = true
            } else {
                if (!sawDigit && Character.isDigit(c)) {
                    currentScore += 1
                    sawDigit = true
                } else {
                    if (!sawUpper || !sawLower) {
                        if (Character.isUpperCase(c)) sawUpper = true else sawLower = true
                        if (sawUpper && sawLower) currentScore += 1
                    }
                }
            }
        }
        if (password.length > REQUIRED_LENGTH) {
            if (REQUIRE_SPECIAL_CHARACTERS && !sawSpecial
                || REQUIRE_UPPER_CASE && !sawUpper
                || REQUIRE_LOWER_CASE && !sawLower
                || REQUIRE_DIGITS && !sawDigit
            ) {
                currentScore = 1
            } else {
                currentScore = 2
                if (password.length > MAXIMUM_LENGTH) {
                    currentScore = 3
                }
            }
        } else {
            currentScore = 0
        }
        when (currentScore) {
            0 -> return "WEAK"
            1 -> return "MEDIUM"
            2 -> return "STRONG"
            3 -> return "VERY_STRONG"
            else -> {
            }
        }
        return "VERY_STRONG"
    }
}