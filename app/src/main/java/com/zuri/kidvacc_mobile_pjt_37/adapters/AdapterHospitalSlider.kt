package com.zuri.kidvacc_mobile_pjt_37.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.zuri.kidvacc_mobile_pjt_37.R
import com.zuri.kidvacc_mobile_pjt_37.models.Hospital
import com.zuri.kidvacc_mobile_pjt_37.ui.hospital.HospitalViewModel
import java.util.*

public class AdapterHospitalSlider(private val act: FragmentActivity, hospitalViewModel: HospitalViewModel) : PagerAdapter() {
    private var hospitalViewModel: HospitalViewModel? = null
    private var onItemClickListener: OnItemClickListener? = null
    private val hospitalPrivate: ArrayList<Hospital> = ArrayList()
    private val hospitalPublic: ArrayList<Hospital> = ArrayList()

    // constructor
    init {
        this.hospitalViewModel = hospitalViewModel
    }

    interface OnItemClickListener {
        fun onItemClick(view: View?, obj: Hospital?)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    override fun getCount(): Int {
        return 2
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as ConstraintLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = act.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v: View = inflater.inflate(R.layout.pager_hospital_model, container, false)

        val hospitalRecyclerView = v.findViewById<RecyclerView>(R.id.hospital_list)
        hospitalRecyclerView.layoutManager = LinearLayoutManager(act, LinearLayoutManager.VERTICAL, false
        )

        when (position) {
            0 -> {
                val appointmentAdapter = HospitalAdapter(hospitalPrivate)
                hospitalRecyclerView.adapter = appointmentAdapter
                hospitalViewModel?.privateHospital?.observe(act,{
                    val data = ArrayList<Hospital>()
                    data.addAll(it)
                    data.removeAll(hospitalPrivate)

                    hospitalPrivate.addAll(data)
                    appointmentAdapter.notifyItemInserted(hospitalPrivate.size - 1)
                    appointmentAdapter.notifyDataSetChanged()
                })
            }
            1 -> {
                val appointmentAdapter = HospitalAdapter(hospitalPublic)
                hospitalRecyclerView.adapter = appointmentAdapter
                hospitalViewModel?.publicHospital?.observe(act,{
                    val data = ArrayList<Hospital>()
                    data.addAll(it)
                    data.removeAll(hospitalPublic)

                    hospitalPublic.addAll(data)
                    appointmentAdapter.notifyItemInserted(hospitalPublic.size - 1)
                    appointmentAdapter.notifyDataSetChanged()
                })
            }
        }

        (container as ViewPager).addView(v)
        return v
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as ConstraintLayout)
    }
}