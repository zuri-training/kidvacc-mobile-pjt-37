package com.zuri.kidvacc_mobile_pjt_37.ui.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.zuri.kidvacc_mobile_pjt_37.R
import com.zuri.kidvacc_mobile_pjt_37.ui.models.Image

public class AdapterImageSlider(private val act: Activity, items: List<Image>) : PagerAdapter() {
    private var items: List<Image>
    private var onItemClickListener: OnItemClickListener? = null

    // constructor
    init {
        this.items = items
    }

    interface OnItemClickListener {
        fun onItemClick(view: View?, obj: Image?)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    override fun getCount(): Int {
        return items.size
    }

    fun getItem(pos: Int): Image {
        return items[pos]
    }

    fun setItems(items: List<Image>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as RelativeLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = act.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v: View = inflater.inflate(R.layout.pager_model, container, false)

        val image = v.findViewById<View>(R.id.image) as ImageView

        val o: Image = items[position]
        image.setImageResource(o.image)


        (container as ViewPager).addView(v)
        return v
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as RelativeLayout)
    }
}