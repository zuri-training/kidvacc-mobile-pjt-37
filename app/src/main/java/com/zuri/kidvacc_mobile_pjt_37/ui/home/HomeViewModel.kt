package com.zuri.kidvacc_mobile_pjt_37.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _quote = MutableLiveData<String>().apply {
        value = "“Vaccination is one of the most effective ways to prevent children from life changing and mostly fatal diseases” - Phil Johnsonna"
    }

    val text: LiveData<String> = _quote
}