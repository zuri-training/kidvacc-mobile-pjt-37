package com.zuri.kidvacc_mobile_pjt_37.ui.book_appointment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.zuri.kidvacc_mobile_pjt_37.R

class PaymentFragment : Fragment() {

    companion object {
        fun newInstance() = PaymentFragment()
    }

    private lateinit var viewModel: PaymentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(this).get(PaymentViewModel::class.java)
        val root = inflater.inflate(R.layout.payment_fragment, container, false)

        root.findViewById<LinearLayout>(R.id.mastercard).setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().remove(this@PaymentFragment).commit();
        }

        root.findViewById<LinearLayout>(R.id.visa).setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().remove(this@PaymentFragment).commit();
        }

        root.findViewById<ImageView>(R.id.back).setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().remove(this@PaymentFragment).commit();
        }
        return root
    }
}