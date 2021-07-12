package com.zuri.kidvacc_mobile_pjt_37.ui.book_appointment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.zuri.kidvacc_mobile_pjt_37.R
import com.zuri.kidvacc_mobile_pjt_37.ui.dashboard.AppointmentViewModel

class ConfirmDetailsFragment : Fragment() {
    private lateinit var appointmentViewModel: AppointmentViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        appointmentViewModel = ViewModelProvider(requireActivity()).get(AppointmentViewModel::class.java)
        var root = inflater.inflate(R.layout.fragment_confirm_details, container, false)

         appointmentViewModel.addAppointment.observe(requireActivity(), {

         })

        root.findViewById<MaterialButton>(R.id.proceed).setOnClickListener {
            val fm: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fm.beginTransaction()
            fragmentTransaction.replace(R.id.fullscreen_frameLayout, PaymentFragment())
            fragmentTransaction.commit()

            requireActivity().supportFragmentManager.beginTransaction().remove(this@ConfirmDetailsFragment).commit();
        }

        root.findViewById<ImageView>(R.id.back).setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().remove(this@ConfirmDetailsFragment).commit();
        }
        return root
    }
}