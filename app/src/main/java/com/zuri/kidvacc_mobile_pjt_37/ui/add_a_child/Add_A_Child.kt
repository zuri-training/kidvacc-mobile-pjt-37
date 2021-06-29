package com.zuri.kidvacc_mobile_pjt_37.ui.add_a_child

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
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
        return root
    }
}