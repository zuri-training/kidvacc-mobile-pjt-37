package com.zuri.kidvacc_mobile_pjt_37.ui.onboarding_page

import android.content.Context
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.zuri.kidvacc_mobile_pjt_37.R
import com.zuri.kidvacc_mobile_pjt_37.ui.adapters.AdapterImageSlider
import com.zuri.kidvacc_mobile_pjt_37.ui.models.Image
import com.zuri.kidvacc_mobile_pjt_37.ui.signup.SignUpPage
import java.util.*

class OnBoardingFragment : Fragment() {
    private var viewPager: ViewPager? = null
    private var layoutDots: LinearLayout? = null
    private var adapterImageSlider: AdapterImageSlider? = null
    private var next: TextView? = null
    private var skip: TextView? = null
    private var login: TextView? = null
    private val arrayImageProduct = intArrayOf(
        R.drawable.undraw_baby_ja7a_1__1_,
        R.drawable.female_doctor_mask_gloves_makes_vaccine_child_patient_173706_412_1,
        R.drawable.undraw_medicine_b1ol_2__1
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_on_boarding, container, false)
        val sharedPref = activity?.getSharedPreferences("com.zuri.kidvacc_mobile_pjt_37", Context.MODE_PRIVATE)

        next = root.findViewById(R.id.next)
        skip = root.findViewById(R.id.skip)
        login = root.findViewById(R.id.login)
        layoutDots = root.findViewById<View>(R.id.layout_dots) as LinearLayout
        viewPager = root.findViewById<View>(R.id.pager) as ViewPager
        adapterImageSlider = AdapterImageSlider(requireActivity(), ArrayList())

        val items: MutableList<Image> = ArrayList()
        for (i in arrayImageProduct) {
            val obj = Image()
            obj.image = i
            obj.imageDrw = ContextCompat.getDrawable(requireActivity(),obj.image)
            items.add(obj)
        }

        adapterImageSlider!!.setItems(items)
        viewPager!!.adapter = adapterImageSlider

        viewPager!!.currentItem = 0
        addBottomDots(layoutDots!!, adapterImageSlider!!.count, 0)
        viewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(pos: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(pos: Int) {
                addBottomDots(layoutDots!!, adapterImageSlider!!.count, pos)
                if (pos == 2){
                    next!!.visibility = View.GONE
                    skip!!.visibility = View.GONE
                    login!!.visibility = View.VISIBLE
                }else{
                    next!!.visibility = View.VISIBLE
                    skip!!.visibility = View.VISIBLE
                    login!!.visibility = View.GONE
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        next!!.setOnClickListener {
            viewPager!!.setCurrentItem(viewPager!!.currentItem+1,true)
        }

        skip!!.setOnClickListener {
            viewPager!!.setCurrentItem(2,true)
        }

        login!!.setOnClickListener {
            openSignUpFragment()
            requireActivity().supportFragmentManager.beginTransaction().remove(this@OnBoardingFragment).commit()
        }
        
        return root
    }

    private fun addBottomDots(layout_dots: LinearLayout, size: Int, current: Int) {
        val dots = arrayOfNulls<ImageView>(size)
        layout_dots.removeAllViews()

        for (i in dots.indices) {
            val widthHeight = 15
            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams(widthHeight, widthHeight))
            params.setMargins(10, 10, 10, 10)

            dots[i] = ImageView(requireActivity())
            dots[i]!!.layoutParams = params
            dots[i]!!.setImageResource(R.drawable.shape_circle)
            //dots[i]!!.setColorFilter(ContextCompat.getColor(requireActivity(), R.color.overlay_dark_10), PorterDuff.Mode.SRC_ATOP)
            layout_dots.addView(dots[i])
        }

        if (dots.isNotEmpty()) {
            dots[current]!!.setColorFilter(ContextCompat.getColor(requireActivity(), R.color.overlay), PorterDuff.Mode.SRC_ATOP)
        }
    }

    private fun openSignUpFragment(){
        val fm: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fm.beginTransaction()
        fragmentTransaction.replace(R.id.fullscreen_frameLayout, SignUpPage())
        fragmentTransaction.commit()
    }
}