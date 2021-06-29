package com.zuri.kidvacc_mobile_pjt_37.ui.onboarding_page

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.zuri.kidvacc_mobile_pjt_37.R
import com.zuri.kidvacc_mobile_pjt_37.ui.adapters.AdapterImageSlider
import com.zuri.kidvacc_mobile_pjt_37.ui.models.Image
import java.util.*

class OnBoardingActivity : AppCompatActivity() {
    private var viewPager: ViewPager? = null
    private var layoutDots: LinearLayout? = null
    private var adapterImageSlider: AdapterImageSlider? = null
    private var next: TextView? = null
    private var skip: TextView? = null
    private var login: TextView? = null
    private val arrayImageProduct = intArrayOf(
        R.drawable.undraw_baby_ja7a_1,
        R.drawable.undraw_baby_ja7a_1,
        R.drawable.undraw_baby_ja7a_1
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on__boarding_)

        next = findViewById(R.id.next)
        skip = findViewById(R.id.skip)
        login = findViewById(R.id.login)
        layoutDots = findViewById<View>(R.id.layout_dots) as LinearLayout
        viewPager = findViewById<View>(R.id.pager) as ViewPager
        adapterImageSlider = AdapterImageSlider(this, ArrayList())

        val items: MutableList<Image> = ArrayList()
        for (i in arrayImageProduct) {
            val obj = Image()
            obj.image = i
            obj.imageDrw = ContextCompat.getDrawable(this,obj.image)
            items.add(obj)
        }

        adapterImageSlider!!.setItems(items)
        viewPager!!.adapter = adapterImageSlider

        viewPager!!.currentItem = 0
        addBottomDots(layoutDots!!, adapterImageSlider!!.count, 0)
        viewPager!!.addOnPageChangeListener(object : OnPageChangeListener {
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
            /*val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)*/
            finish()
        }
    }

    private fun addBottomDots(layout_dots: LinearLayout, size: Int, current: Int) {
        val dots = arrayOfNulls<ImageView>(size)
        layout_dots.removeAllViews()

        for (i in dots.indices) {
            val widthHeight = 15
            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams(widthHeight, widthHeight))
            params.setMargins(10, 10, 10, 10)

            dots[i] = ImageView(this)
            dots[i]!!.layoutParams = params
            dots[i]!!.setImageResource(R.drawable.shape_circle)
            //dots[i]!!.setColorFilter(ContextCompat.getColor(this, R.color.overlay_dark_10), PorterDuff.Mode.SRC_ATOP)
            layout_dots.addView(dots[i])
        }

        if (dots.isNotEmpty()) {
            dots[current]!!.setColorFilter(ContextCompat.getColor(this, R.color.overlay), PorterDuff.Mode.SRC_ATOP)
        }
    }

    override fun onBackPressed() {
        finish()
    }
}