package com.zuri.kidvacc_mobile_pjt_37.ui.book_appointment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.zuri.kidvacc_mobile_pjt_37.R
import com.zuri.kidvacc_mobile_pjt_37.ui.dashboard.AppointmentViewModel
import java.util.*

class BookAppointmentFragment : Fragment() {
    companion object {
        fun newInstance() = BookAppointmentFragment()
    }
    private lateinit var appointmentViewModel: AppointmentViewModel
    private lateinit var childList: ArrayList<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        appointmentViewModel = ViewModelProvider(requireActivity()).get(AppointmentViewModel::class.java)
        val root = inflater.inflate(R.layout.book_appointment_fragment, container, false)

        root.findViewById<ImageButton>(R.id.back).setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().remove(this@BookAppointmentFragment).commit();
        }

        root.findViewById<MaterialButton>(R.id.schedule_appointment).setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().remove(this@BookAppointmentFragment).commit();
        }

        return root
    }
}