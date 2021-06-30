package com.zuri.kidvacc_mobile_pjt_37.ui.add_a_child

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatSpinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.zuri.kidvacc_mobile_pjt_37.R


class Add_A_Child : Fragment() {

    companion object {
        fun newInstance() = Add_A_Child()
    }

    private lateinit var viewModel: AddAChildViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(this).get(AddAChildViewModel::class.java)
        val root = inflater.inflate(R.layout.add__a__child_fragment, container, false)

        /*requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().supportFragmentManager.beginTransaction().remove(this@Add_A_Child).commit();
            }
        })*/
        setupSpinners(root)
        return root
    }

    private fun setupSpinners(root:View){
        //HardCoded The Values Just For Now, It might change later
        val years = arrayOf("2000","2001","2002","2003","2004","2005","2006","2007","2008","2009","2010","2011",
            "2012","2013","2014","2015","2016","2017","2018","2019","2020","2021")
        val months = arrayOf("January","February","March","April","May","June","July","August","September","October","November","December")
        val genders = arrayOf("Male","Female")

        val daySpinner = root.findViewById<AppCompatSpinner>(R.id.day_spinner)
        val monthSpinner = root.findViewById<AppCompatSpinner>(R.id.month_spinner)
        val yearSpinner = root.findViewById<AppCompatSpinner>(R.id.year_spinner)
        val genderSpinner = root.findViewById<AppCompatSpinner>(R.id.gender_spinner)

        val aMonth: ArrayAdapter<String> = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, months)
        aMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        monthSpinner.adapter = aMonth

        monthSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }
        }

        val aYears: ArrayAdapter<String> = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, years)
        aYears.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        yearSpinner.adapter = aYears

        val aGender: ArrayAdapter<String> = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, genders)
        aGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner.adapter = aGender
    }
}