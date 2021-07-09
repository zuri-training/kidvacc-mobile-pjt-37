package com.zuri.kidvacc_mobile_pjt_37.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.google.android.material.button.MaterialButton
import com.zuri.kidvacc_mobile_pjt_37.R
import com.zuri.kidvacc_mobile_pjt_37.adapters.AppointmentAdapter
import com.zuri.kidvacc_mobile_pjt_37.models.Appointment
import com.zuri.kidvacc_mobile_pjt_37.networking.VolleyAuth
import com.zuri.kidvacc_mobile_pjt_37.networking.VolleySingleton
import com.zuri.kidvacc_mobile_pjt_37.ui.book_appointment.BookAppointmentFragment
import java.util.*

class AppointmentFragment : Fragment() {
    private lateinit var appointmentViewModel: AppointmentViewModel
    private lateinit var dataSet: ArrayList<Appointment>

    //Todo: Rename DashboardFragment
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        appointmentViewModel = ViewModelProvider(requireActivity()).get(AppointmentViewModel::class.java)
        dataSet = ArrayList()
        val root = inflater.inflate(R.layout.fragment_appointment, container, false)

        val appointmentAdapter = AppointmentAdapter(dataSet)
        val appointmentList: RecyclerView = root.findViewById(R.id.appointment_list)
        appointmentList.adapter = appointmentAdapter

        appointmentViewModel.addAppointment.observe(requireActivity(), {
            dataSet.add(it)
            appointmentAdapter.notifyItemInserted(dataSet.size-1)
        })

        root.findViewById<MaterialButton>(R.id.book_appointment).setOnClickListener {
            openBookAppointmentFragment()
            getChild()
        }
        return root
    }

    private fun openBookAppointmentFragment(){
        val fm: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fm.beginTransaction()
        fragmentTransaction.replace(R.id.fullscreen_frameLayout, BookAppointmentFragment())
        fragmentTransaction.commit()
    }

    private fun getChild(){
        val jsonArrayRequest: JsonArrayRequest = object : JsonArrayRequest(
            Method.GET, VolleyAuth.URL_CHILD_LIST, null,
            Response.Listener { response ->
                for (i in 0 until response.length()){
                    val jsonObject = response.getJSONObject(i)
                    val firstName = jsonObject.getString("First_name")
                    Log.i("Name", firstName)
                }},
            Response.ErrorListener { error -> error.printStackTrace() }) {
            override fun getHeaders(): Map<String, String> {
                val headers: HashMap<String, String> = HashMap<String, String>()
                headers.put("Authorization", "Token "+VolleyAuth.TOKEN)
                return headers
            }
        }

        VolleySingleton.getInstance(requireActivity()).addToRequestQueue(jsonArrayRequest)
    }
}