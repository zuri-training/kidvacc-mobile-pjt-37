package com.zuri.kidvacc_mobile_pjt_37.ui.settings

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.android.volley.Request
import com.android.volley.request.SimpleMultiPartRequest
import com.google.android.material.card.MaterialCardView
import com.shashank.sony.fancytoastlib.FancyToast
import com.zuri.kidvacc_mobile_pjt_37.R
import com.zuri.kidvacc_mobile_pjt_37.networking.VolleyAuth
import com.zuri.kidvacc_mobile_pjt_37.networking.VolleySingleton
import com.zuri.kidvacc_mobile_pjt_37.ui.signup.SignUpPage
import java.util.*

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        root.findViewById<AppCompatImageView>(R.id.iv_back).setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction().remove(this@SettingsFragment).commit();
        }

        root.findViewById<MaterialCardView>(R.id.cardView_log_out).setOnClickListener{
            logout()
        }
        // Inflate the layout for this fragment
        return root
    }

    fun logout(
    ){
        val sharedPref = activity?.getSharedPreferences(
            "com.zuri.kidvacc_mobile_pjt_37",
            Context.MODE_PRIVATE
        )

        val progressDialog = ProgressDialog(requireActivity())
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Signing Out")
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()

        val headers: HashMap<String, String> = HashMap<String, String>()
        headers["Authorization"] = "Token "+ VolleyAuth.TOKEN

        val request = SimpleMultiPartRequest(
            Request.Method.POST,
            VolleyAuth.URL_CHILD_LIST,
            { response ->
                requireActivity().onBackPressed();
                Log.i("Response", response)
                progressDialog.dismiss()
                sharedPref?.edit()?.putBoolean("Open SignUp Screen", true)?.apply()
                FancyToast.makeText(requireActivity(),"Logged Out!",
                    FancyToast.LENGTH_SHORT,
                    FancyToast.SUCCESS,false).show();
                val fm: FragmentManager = requireActivity().supportFragmentManager
                val fragmentTransaction: FragmentTransaction = fm.beginTransaction()
                fragmentTransaction.replace(R.id.fullscreen_frameLayout, SignUpPage())
                fragmentTransaction.commit()
            },
            { error ->
                requireActivity().onBackPressed();
                error.printStackTrace()
                progressDialog.dismiss()
                sharedPref?.edit()?.putBoolean("Open SignUp Screen", true)?.apply()
                FancyToast.makeText(requireActivity(),"Logged Out!",
                    FancyToast.LENGTH_SHORT,
                    FancyToast.SUCCESS,false).show();
                val fm: FragmentManager = requireActivity().supportFragmentManager
                val fragmentTransaction: FragmentTransaction = fm.beginTransaction()
                fragmentTransaction.replace(R.id.fullscreen_frameLayout, SignUpPage())
                fragmentTransaction.commit()
            })

        request.headers = headers
        VolleySingleton.getInstance(requireActivity()).addToRequestQueue(request)
    }
}