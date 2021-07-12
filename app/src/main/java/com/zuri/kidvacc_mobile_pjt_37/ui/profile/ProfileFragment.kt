package com.zuri.kidvacc_mobile_pjt_37.ui.profile

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.request.SimpleMultiPartRequest
import com.google.android.material.button.MaterialButton
import com.shashank.sony.fancytoastlib.FancyToast
import com.zuri.kidvacc_mobile_pjt_37.R
import com.zuri.kidvacc_mobile_pjt_37.networking.VolleyAuth
import com.zuri.kidvacc_mobile_pjt_37.networking.VolleySingleton
import com.zuri.kidvacc_mobile_pjt_37.ui.add_a_child.Add_A_Child
import com.zuri.kidvacc_mobile_pjt_37.ui.settings.SettingsFragment
import com.zuri.kidvacc_mobile_pjt_37.ui.signup.SignUpPage
import java.util.*

class ProfileFragment : Fragment() {
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        root.findViewById<LinearLayout>(R.id.settings).setOnClickListener {
            val fm: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fm.beginTransaction()
            fragmentTransaction.replace(R.id.home_frameLayout, SettingsFragment())
            fragmentTransaction.commit()
        }

        root.findViewById<Button>(R.id.add_a_child).setOnClickListener {
            val fm: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fm.beginTransaction()
            fragmentTransaction.replace(R.id.fullscreen_frameLayout, Add_A_Child())
            fragmentTransaction.commit()
        }

        root.findViewById<ImageView>(R.id.back).setOnClickListener {
            requireActivity().onBackPressed();
        }

        root.findViewById<MaterialButton>(R.id.sign_out).setOnClickListener {
            logout()
        }
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
        progressDialog.setMessage("Adding Child")
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