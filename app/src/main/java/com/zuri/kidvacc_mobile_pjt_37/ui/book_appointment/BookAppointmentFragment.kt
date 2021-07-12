package com.zuri.kidvacc_mobile_pjt_37.ui.book_appointment

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.google.android.material.button.MaterialButton
import com.zuri.kidvacc_mobile_pjt_37.R
import com.zuri.kidvacc_mobile_pjt_37.models.Appointment
import com.zuri.kidvacc_mobile_pjt_37.networking.VolleyAuth
import com.zuri.kidvacc_mobile_pjt_37.networking.VolleySingleton
import com.zuri.kidvacc_mobile_pjt_37.ui.dashboard.AppointmentViewModel
import java.util.*
import kotlin.collections.ArrayList

class BookAppointmentFragment : Fragment() {
    companion object {
        fun newInstance() = BookAppointmentFragment()
    }
    private lateinit var appointmentViewModel: AppointmentViewModel
    private lateinit var childList: ArrayList<String>
    private lateinit var childAdapter : ArrayAdapter<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        appointmentViewModel = ViewModelProvider(requireActivity()).get(AppointmentViewModel::class.java)
        childList = ArrayList()
        val root = inflater.inflate(R.layout.book_appointment_fragment, container, false)

        val vaccines = listOf("Polio (IPV)", "Pneumococcal (PCV)", "Rotavirus (RV)", "Hepatitis B", "Diphtheria, tetanus, and whooping cough (pertussis) (DTaP)", "Haemophilus influenzae type b (Hib)")
        val hospitals = listOf("ST. CLAIRE’S PAEDIATRICS", "NEW LIFE HOSPITAL", "REDDINGTON HOSPITAL", "LAGOON HOSPITAL", "HOLY CROSS MATERNITY" +
                "UNIVERSITY TEACHING HOSPITAL,UNIVERSITY TEACHING HOSPITAL", "FEDERAL HOSPITAL")
        val hospitalsType = listOf("Public", "Private")
        val payments = listOf("Debit Card", "Transfer")
        val location = listOf("Badagry", "Lagos", "Yaba", "Akure")

        childAdapter = ArrayAdapter(requireContext(), R.layout.list_item, childList)
        val vaccinesAdapter = ArrayAdapter(requireContext(), R.layout.list_item, vaccines)
        val hospitalsTypeAdapter = ArrayAdapter(requireContext(), R.layout.list_item, hospitalsType)
        val hospitalsAdapter = ArrayAdapter(requireContext(), R.layout.list_item, hospitals)
        val paymentsAdapter = ArrayAdapter(requireContext(), R.layout.list_item, payments)
        val locationAdapter = ArrayAdapter(requireContext(), R.layout.list_item, location)

        root.findViewById<AutoCompleteTextView>(R.id.name).setAdapter(childAdapter);
        root.findViewById<AutoCompleteTextView>(R.id.vaccines).setAdapter(vaccinesAdapter);
        root.findViewById<AutoCompleteTextView>(R.id.hospital).setAdapter(hospitalsAdapter);
        root.findViewById<AutoCompleteTextView>(R.id.hospital_type).setAdapter(hospitalsTypeAdapter);
        root.findViewById<AutoCompleteTextView>(R.id.pay_method).setAdapter(paymentsAdapter);
        root.findViewById<AutoCompleteTextView>(R.id.location).setAdapter(locationAdapter);

        root.findViewById<ImageButton>(R.id.back).setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().remove(this@BookAppointmentFragment).commit();
        }

        root.findViewById<MaterialButton>(R.id.schedule_appointment).setOnClickListener {
           // val addAppointment: MutableLiveData<Appointment> = appointmentViewModel.addAppointment
            val addAppointmentList: MutableLiveData<java.util.ArrayList<Appointment>> = appointmentViewModel.addAppointmentList
            val appointment = Appointment()

            var appointmentList:java.util.ArrayList<Appointment> = java.util.ArrayList<Appointment>()
            if (addAppointmentList.value != null){
                appointmentList = addAppointmentList.value!!
            }

            appointment.name = root.findViewById<AutoCompleteTextView>(R.id.name).text.toString()
            appointment.vaccine = root.findViewById<AutoCompleteTextView>(R.id.vaccines).text.toString()
            appointment.time = "10:20 AM"
            appointment.hospital = root.findViewById<AutoCompleteTextView>(R.id.hospital).text.toString()
            appointment.date_due = "2021-09-10"
            appointment.id = System.currentTimeMillis()

            appointmentList.add(appointment)
            addAppointmentList.postValue(appointmentList)
            //addAppointment.postValue(appointment)

            requireActivity().supportFragmentManager.beginTransaction().remove(this@BookAppointmentFragment).commit();
        }

        getChild(root)
        return root
    }

    private fun getChild(root:View){
        val progressDialog = ProgressDialog(requireActivity())
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Getting Details")
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()

        val jsonArrayRequest: JsonArrayRequest = object : JsonArrayRequest(
            Method.GET, VolleyAuth.URL_CHILD_LIST, null,
            Response.Listener { response ->
                progressDialog.dismiss()
                requireActivity().findViewById<RelativeLayout>(R.id.progressBarLayout).visibility = View.GONE
                Log.i("response",response.toString())
                for (i in 0 until response.length()){
                    val jsonObject = response.getJSONObject(i)
                    val firstName = jsonObject.getString("First_name")
                    val middleName = jsonObject.getString("Middle_name")
                    val lastName = jsonObject.getString("Last_name")

                    childList.add("$firstName $lastName")
                    childAdapter.notifyDataSetChanged()
                   // root.findViewById<AutoCompleteTextView>(R.id.name).setAdapter(childAdapter);
                }},
            Response.ErrorListener { error -> error.printStackTrace()
                progressDialog.dismiss()}) {
            override fun getHeaders(): Map<String, String> {
                val headers: HashMap<String, String> = HashMap<String, String>()
                headers["Authorization"] = "Token "+ VolleyAuth.TOKEN
                return headers
            }
        }

        VolleySingleton.getInstance(requireActivity()).addToRequestQueue(jsonArrayRequest)
    }
}