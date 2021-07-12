package com.zuri.kidvacc_mobile_pjt_37.ui.vaccines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.zuri.kidvacc_mobile_pjt_37.R

class VaccineFragment : Fragment() {

    companion object {
        fun newInstance() = VaccineFragment()
    }

    private lateinit var viewModel: VaccineViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(this).get(VaccineViewModel::class.java)
        var root = inflater.inflate(R.layout.vaccine_fragment, container, false)

        root.findViewById<ImageView>(R.id.back).setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().remove(this@VaccineFragment).commit();
        }

        return root
    }

}