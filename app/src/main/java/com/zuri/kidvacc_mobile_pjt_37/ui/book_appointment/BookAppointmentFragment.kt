package com.zuri.kidvacc_mobile_pjt_37.ui.book_appointment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.zuri.kidvacc_mobile_pjt_37.R

class BookAppointmentFragment : Fragment() {

    companion object {
        fun newInstance() = BookAppointmentFragment()
    }

    private lateinit var viewModel: BookAppointmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(this).get(BookAppointmentViewModel::class.java)
        return inflater.inflate(R.layout.book_appointment_fragment, container, false)
    }
}