package com.zuri.kidvacc_mobile_pjt_37.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.zuri.kidvacc_mobile_pjt_37.R
import com.zuri.kidvacc_mobile_pjt_37.models.Appointment
import java.util.*

class AppointmentAdapter (private val dataSet: ArrayList<Appointment>) : RecyclerView.Adapter< AppointmentAdapter.ViewHolder>() {
    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }
    
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView
        val vaccine: TextView
        val hospital: TextView
        val date_due: TextView
        val time: TextView
        val cancel: MaterialButton
        val viewColor: View

        init {
            // Define click listener for the ViewHolder's View.
            name = view.findViewById(R.id.name)
            vaccine = view.findViewById(R.id.vaccine)
            hospital = view.findViewById(R.id.hospital)
            date_due = view.findViewById(R.id.date)
            time = view.findViewById(R.id.time)
            cancel = view.findViewById(R.id.cancel)
            viewColor = view.findViewById(R.id.view)
        }
    }
    
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.appointment_model, viewGroup, false)

        return ViewHolder(view)
    }
    
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val appointment = dataSet[position]
        viewHolder.name.text = appointment.name
        viewHolder.vaccine.text = appointment.vaccine
        viewHolder.date_due.text = appointment.date_due
        viewHolder.hospital.text = appointment.hospital
        viewHolder.time.text = appointment.time

        viewHolder.cancel.setOnClickListener {
            onItemClickListener?.onItemClick(it,position)
        }
    }
    
    override fun getItemCount() = dataSet.size
}
