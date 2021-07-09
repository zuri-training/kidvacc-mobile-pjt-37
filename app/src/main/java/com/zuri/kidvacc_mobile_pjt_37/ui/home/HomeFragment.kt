package com.zuri.kidvacc_mobile_pjt_37.ui.home

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.volley.*
import com.android.volley.toolbox.JsonArrayRequest
import com.zuri.kidvacc_mobile_pjt_37.R
import com.zuri.kidvacc_mobile_pjt_37.networking.VolleyAuth
import com.zuri.kidvacc_mobile_pjt_37.networking.VolleySingleton
import com.zuri.kidvacc_mobile_pjt_37.ui.book_appointment.BookAppointmentFragment
import com.zuri.kidvacc_mobile_pjt_37.ui.infobase.Info_Base_Fragment
import com.zuri.kidvacc_mobile_pjt_37.ui.onboarding_page.OnBoardingFragment
import com.zuri.kidvacc_mobile_pjt_37.ui.signup.SignUpPage
import com.zuri.kidvacc_mobile_pjt_37.ui.vaccines.VaccineFragment
import java.util.*


class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val sharedPref = requireActivity().getSharedPreferences(
            "com.zuri.kidvacc_mobile_pjt_37",
            Context.MODE_PRIVATE
        )
        val openOnBoardingScreen = sharedPref.getBoolean("Open OnBoarding Screen", true)
        val openSignUpScreen = sharedPref.getBoolean("Open SignUp Screen", true)

        val quoteText = root.findViewById<TextView>(R.id.quote_text)

        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            val quote = it.toString().split("-")
            val spannable = SpannableStringBuilder(it)
            spannable.setSpan(
                StyleSpan(Typeface.BOLD),
                0,
                quote[0].length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannable.setSpan(
                StyleSpan(Typeface.ITALIC),
                quote[0].length,
                spannable.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            quoteText.text = spannable
        })

        root.findViewById<View>(R.id.relativeLayout).setOnClickListener {

        }

        root.findViewById<View>(R.id.infobase).setOnClickListener {
            val fm: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fm.beginTransaction()
            fragmentTransaction.replace(R.id.home_frameLayout, Info_Base_Fragment())
            /*fragmentTransaction.setReorderingAllowed(true)
            fragmentTransaction.addToBackStack(null)*/
            fragmentTransaction.commit()
        }

        root.findViewById<View>(R.id.vaccines).setOnClickListener {
            val fm: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fm.beginTransaction()
            fragmentTransaction.replace(R.id.home_frameLayout, VaccineFragment())
            fragmentTransaction.commit()
        }

        root.findViewById<View>(R.id.book_appointment).setOnClickListener {
            val fm: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fm.beginTransaction()
            fragmentTransaction.replace(R.id.home_frameLayout, BookAppointmentFragment())
            fragmentTransaction.commit()
        }

        if (VolleyAuth.TOKEN.equals("0")){
            if (openOnBoardingScreen){
                openOnBoardingFragment()
            }else if (openSignUpScreen){
                openSignUpFragment()
            }
        }
        return root
    }

    private fun openOnBoardingFragment(){
        val fm: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fm.beginTransaction()
        fragmentTransaction.replace(R.id.fullscreen_frameLayout, OnBoardingFragment())
        fragmentTransaction.commit()
    }

    private fun openSignUpFragment(){
        val fm: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fm.beginTransaction()
        fragmentTransaction.replace(R.id.fullscreen_frameLayout, SignUpPage())
        fragmentTransaction.commit()
    }

    private fun getChild(){
        val jsonArrayRequest: JsonArrayRequest = object : JsonArrayRequest(Method.GET, VolleyAuth.URL_PAYMENTS, null,
            Response.Listener { response -> Log.i("Response", response.toString()) },
            Response.ErrorListener { error -> error.printStackTrace() }) {
            override fun getHeaders(): Map<String, String> {
                val headers: HashMap<String, String> = HashMap<String, String>()
                headers.put("Authorization", "Token 7d87e7bef6592867b9e1a3d1210d476f17d2d361")
                return headers
            }
        }

        VolleySingleton.getInstance(requireActivity()).addToRequestQueue(jsonArrayRequest)
    }
}