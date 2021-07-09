package com.zuri.kidvacc_mobile_pjt_37.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zuri.kidvacc_mobile_pjt_37.models.Appointment

class AppointmentViewModel : ViewModel() {
    var addAppointment: MutableLiveData<Appointment> = MutableLiveData()
}