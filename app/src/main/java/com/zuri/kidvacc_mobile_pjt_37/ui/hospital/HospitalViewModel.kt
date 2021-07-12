package com.zuri.kidvacc_mobile_pjt_37.ui.hospital

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zuri.kidvacc_mobile_pjt_37.models.Hospital
import java.util.*

class HospitalViewModel : ViewModel() {
    var publicHospital: MutableLiveData<ArrayList<Hospital>> = MutableLiveData()
    var privateHospital: MutableLiveData<ArrayList<Hospital>> = MutableLiveData()
}