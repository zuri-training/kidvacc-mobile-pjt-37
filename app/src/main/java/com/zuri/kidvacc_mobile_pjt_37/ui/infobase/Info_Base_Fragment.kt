package com.zuri.kidvacc_mobile_pjt_37.ui.infobase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.zuri.kidvacc_mobile_pjt_37.R

class Info_Base_Fragment : Fragment() {

    companion object {
        fun newInstance() = Info_Base_Fragment()
    }

    private lateinit var viewModel: InfoBaseViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(this).get(InfoBaseViewModel::class.java)
        val root = inflater.inflate(R.layout.info__base__fragment, container, false)

        root.findViewById<Button>(R.id.submit).setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().remove(this@Info_Base_Fragment).commit();
        }

        return root
    }

}