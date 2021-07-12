package com.zuri.kidvacc_mobile_pjt_37.ui.hospital

import android.app.ProgressDialog
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.android.volley.Response
import com.android.volley.request.JsonArrayRequest
import com.zuri.kidvacc_mobile_pjt_37.R
import com.zuri.kidvacc_mobile_pjt_37.adapters.AdapterHospitalSlider
import com.zuri.kidvacc_mobile_pjt_37.models.Hospital
import com.zuri.kidvacc_mobile_pjt_37.networking.VolleyAuth
import com.zuri.kidvacc_mobile_pjt_37.networking.VolleySingleton
import java.util.*


class HospitalFragment : Fragment() {
    private lateinit var viewModel: HospitalViewModel
    private var viewPager: ViewPager? = null
    private var privateHospitalTextView: TextView? = null
    private var publicHospitalTextView: TextView? = null
    private var adapterHospitalSlider: AdapterHospitalSlider? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(this).get(HospitalViewModel::class.java)
        val root = inflater.inflate(R.layout.hospital_fragment, container, false)

        privateHospitalTextView = root.findViewById(R.id.private_hospital)
        publicHospitalTextView = root.findViewById(R.id.public_hosital)

        makeBold(privateHospitalTextView)
        getHospital(root)
        return root
    }

    private fun getHospital(root: View){
        val hospitalPrivate: ArrayList<Hospital> = ArrayList()
        val hospitalPublic: ArrayList<Hospital> = ArrayList()

        val progressDialog = ProgressDialog(requireActivity())
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Getting Hospitals")
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()

        val jsonArrayRequest: JsonArrayRequest = object : JsonArrayRequest(
            Method.GET, VolleyAuth.URL_HOSPITAL_TYPE, null,
            Response.Listener { response ->
                progressDialog.dismiss()
                requireActivity().findViewById<RelativeLayout>(R.id.progressBarLayout).visibility =
                    View.GONE
                Log.i("response", response.toString())
                for (i in 0 until response.length()) {
                    val jsonObject = response.getJSONObject(i)
                    val type = jsonObject.getString("hospital_type")
                    val name = jsonObject.getString("name")

                    val hospital = Hospital()
                    hospital.name = name
                    hospital.type = type

                    if (type.equals("public")) {
                        hospitalPublic.add(hospital)
                    } else {
                        hospitalPrivate.add(hospital)
                    }
                    if (i == response.length() - 1) {
                        val hospitalPublic1: MutableLiveData<ArrayList<Hospital>> =
                            viewModel.publicHospital
                        hospitalPublic1.postValue(hospitalPublic)
                        val hospitalPrivate1: MutableLiveData<ArrayList<Hospital>> =
                            viewModel.privateHospital
                        hospitalPrivate1.postValue(hospitalPrivate)

                        adapterHospitalSlider = AdapterHospitalSlider(requireActivity(), viewModel)
                        viewPager = root.findViewById<View>(R.id.pager) as ViewPager
                        viewPager!!.adapter = adapterHospitalSlider
                        viewPager!!.addOnPageChangeListener(object :
                            ViewPager.OnPageChangeListener {
                            override fun onPageScrolled(
                                pos: Int,
                                positionOffset: Float,
                                positionOffsetPixels: Int
                            ) {
                            }

                            override fun onPageSelected(pos: Int) {
                                if (pos == 1) {
                                    makeNormal(privateHospitalTextView)
                                    makeBold(publicHospitalTextView)
                                } else {
                                    makeBold(privateHospitalTextView)
                                    makeNormal(publicHospitalTextView)
                                }
                            }

                            override fun onPageScrollStateChanged(state: Int) {}
                        })
                        publicHospitalTextView?.setOnClickListener {
                            viewPager!!.setCurrentItem(1, true)
                        }

                        privateHospitalTextView?.setOnClickListener {
                            viewPager!!.setCurrentItem(0, true)
                        }
                    }
                    // root.findViewById<AutoCompleteTextView>(R.id.name).setAdapter(childAdapter);
                }
            },
            Response.ErrorListener { error ->
                error.printStackTrace()
                progressDialog.dismiss()
            }) {
            override fun getHeaders(): Map<String, String> {
                val headers: HashMap<String, String> = HashMap<String, String>()
                headers["Authorization"] = "Token "+ VolleyAuth.TOKEN
                return headers
            }
        }

        VolleySingleton.getInstance(requireActivity()).addToRequestQueue(jsonArrayRequest)
    }

    private fun makeBold(textView: TextView?){
        val quote = textView?.text
        val spannable = SpannableStringBuilder(quote)
        if (quote != null) {
            val spans: Array<StyleSpan> = spannable.getSpans(0, quote.length, StyleSpan::class.java)
            for (element in spans) {
                spannable.removeSpan(element)
            }
            spannable.setSpan(StyleSpan(Typeface.BOLD), 0, quote.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        if (textView != null) {
            textView.text = spannable
        }
    }

    private fun makeNormal(textView: TextView?){
        val quote = textView?.text
        val spannable = SpannableStringBuilder(quote)
        if (quote != null) {
            val spans: Array<StyleSpan> = spannable.getSpans(0, quote.length, StyleSpan::class.java)
            for (element in spans) {
                spannable.removeSpan(element)
            }
            spannable.setSpan(StyleSpan(Typeface.NORMAL), 0, quote.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        if (textView != null) {
            textView.text = spannable
        }
    }
}