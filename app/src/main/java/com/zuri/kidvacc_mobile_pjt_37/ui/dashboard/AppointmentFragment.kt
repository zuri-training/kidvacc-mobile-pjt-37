package com.zuri.kidvacc_mobile_pjt_37.ui.dashboard

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.request.JsonArrayRequest
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
    private lateinit var childList: ArrayList<String>

    //Todo: Rename DashboardFragment
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        appointmentViewModel = ViewModelProvider(requireActivity()).get(AppointmentViewModel::class.java)
        childList = ArrayList()
        dataSet = ArrayList()
        val root = inflater.inflate(R.layout.fragment_appointment, container, false)

        val appointmentAdapter = AppointmentAdapter(dataSet)
        val appointmentList: RecyclerView = root.findViewById(R.id.appointment_list)
        appointmentList.layoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false
        )
        appointmentList.adapter = appointmentAdapter
        appointmentAdapter.setOnItemClickListener(object : AppointmentAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int) {
                val addAppointmentList: MutableLiveData<ArrayList<Appointment>> = appointmentViewModel.addAppointmentList
                dataSet.removeAt(position)
                appointmentAdapter.notifyItemRemoved(position)
                appointmentAdapter.notifyDataSetChanged()
                addAppointmentList.postValue(dataSet)
            }
        })

        /* appointmentViewModel.addAppointment.observe(requireActivity(), {
             dataSet.add(it)
             appointmentAdapter.notifyItemInserted(dataSet.size-1)
         })*/

        appointmentViewModel.addAppointmentList.observe(requireActivity(), {
            val data = ArrayList<Appointment>()
            data.addAll(it)
            data.removeAll(dataSet)

            dataSet.addAll(data)
            appointmentAdapter.notifyItemInserted(dataSet.size - 1)
            appointmentAdapter.notifyDataSetChanged()
        })

        root.findViewById<MaterialButton>(R.id.book_appointment).setOnClickListener {
            openBookAppointmentFragment()
        }

        root.findViewById<ImageView>(R.id.back).setOnClickListener {
            requireActivity().onBackPressed();
        }
        if (appointmentViewModel.addAppointmentList.value == null){
            getChild(root)
        }
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
                    if(i >= response.length()-1){
                        getAppointment(root)
                    }
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

    private fun getAppointment(root:View){
        val hospitals = listOf("ST. CLAIRE’S PAEDIATRICS", "NEW LIFE HOSPITAL", "REDDINGTON HOSPITAL", "LAGOON HOSPITAL", "HOLY CROSS MATERNITY" +
                "UNIVERSITY TEACHING HOSPITAL,UNIVERSITY TEACHING HOSPITAL", "FEDERAL HOSPITAL")
        val vaccines = listOf("BCG (Tubercolosis)", "Whopping Cough", "Measles",
            "Polio (IPV)", "Pneumococcal (PCV)", "Rotavirus (RV)", "Hepatitis B", "Diphtheria, tetanus, and whooping cough (pertussis) (DTaP)", "Haemophilus influenzae type b (Hib)")

        val addAppointmentList: MutableLiveData<ArrayList<Appointment>> = appointmentViewModel.addAppointmentList
        var appointmentList:ArrayList<Appointment> = ArrayList<Appointment>()
        if (addAppointmentList.value != null){
            appointmentList = addAppointmentList.value!!
        }

        val progressDialog = ProgressDialog(requireActivity())
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Getting Details")
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()

        val jsonArrayRequest: JsonArrayRequest = object : JsonArrayRequest(
            Method.GET, VolleyAuth.URL_APPOINTMENT, null,
            Response.Listener { response ->
                progressDialog.dismiss()
                requireActivity().findViewById<RelativeLayout>(R.id.progressBarLayout).visibility = View.GONE
                Log.i("response",response.toString())
                for (i in 0 until response.length()){
                    val jsonObject = response.getJSONObject(i)
                    val childPos = jsonObject.getInt("child")
                    val date = jsonObject.getString("date")
                    val child = childList[childPos-1]
                    var hospital = hospitals[0]
                    if (childPos<hospitals.size){
                        hospital = hospitals[childPos-1]
                    }

                    val appointment = Appointment()
                    appointment.name = child
                    appointment.vaccine = vaccines[childPos-1]
                    appointment.time = "10:20 AM"
                    appointment.hospital = hospital
                    appointment.date_due = date
                    appointment.id = System.currentTimeMillis()

                    appointmentList.add(appointment)
                    addAppointmentList.postValue(appointmentList)
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

    private fun openBookAppointmentFragment(){
        val fm: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fm.beginTransaction()
        fragmentTransaction.replace(R.id.fullscreen_frameLayout, BookAppointmentFragment())
        fragmentTransaction.commit()
    }
}