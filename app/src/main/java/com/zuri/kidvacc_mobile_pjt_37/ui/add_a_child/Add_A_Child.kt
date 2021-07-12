package com.zuri.kidvacc_mobile_pjt_37.ui.add_a_child

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.appcompat.widget.AppCompatSpinner
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.imageview.ShapeableImageView
import com.zuri.kidvacc_mobile_pjt_37.R
import java.io.FileNotFoundException


class Add_A_Child : Fragment() {

    companion object {
        fun newInstance() = Add_A_Child()
    }

    private lateinit var viewModel: AddAChildViewModel
    private lateinit var childPicturePreview: ShapeableImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(AddAChildViewModel::class.java)
        val root = inflater.inflate(R.layout.add__a__child_fragment, container, false)

        childPicturePreview = root.findViewById(R.id.shapeableImageView)
        /*requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

            }
        })*/
        root.findViewById<ImageView>(R.id.back).setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().remove(this@Add_A_Child).commit();
        }

        root.findViewById<Button>(R.id.edit_profile).setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().remove(this@Add_A_Child).commit();
        }

        childPicturePreview.setOnClickListener {
            chooseLogo.launch("image/*")
        }
        setupSpinners(root)
        return root
    }

    private fun setupSpinners(root: View){
        //HardCoded The Values Just For Now, It might change later
        val years = 2000..2021
        val months = arrayOf(
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
        )
        val days = 1..31
        val genders = arrayOf("Male", "Female")
        val bloodGroup = arrayOf("A+", "A-", "B", "B-", "O+", "O-", "AB+", "AB-")
        val genotype = arrayOf("AA", "AS", "SS", "AC")

        val daySpinner = root.findViewById<AppCompatSpinner>(R.id.day_spinner)
        val monthSpinner = root.findViewById<AppCompatSpinner>(R.id.month_spinner)
        val yearSpinner = root.findViewById<AppCompatSpinner>(R.id.year_spinner)
        val genderSpinner = root.findViewById<AppCompatSpinner>(R.id.gender_spinner)
        val bloodGroupSpinner = root.findViewById<AppCompatSpinner>(R.id.blood_group_spinner)
        val genotypeSpinner = root.findViewById<AppCompatSpinner>(R.id.genotype_spinner)

        val aMonth: ArrayAdapter<String> = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_item,
            months
        )
        aMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        monthSpinner.adapter = aMonth

        val aBloodGroup: ArrayAdapter<String> = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_item,
            bloodGroup
        )
        aBloodGroup.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bloodGroupSpinner.adapter = aBloodGroup

        val aGenotype: ArrayAdapter<String> = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_item,
            genotype
        )
        aGenotype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genotypeSpinner.adapter = aGenotype

        /*monthSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }
        }*/

        var year: String?
        val yearList = ArrayList<String>()
        years.forEach{ y ->
            year = y.toString()
            year?.let { yearList.add(it) }
        }
        val aYears: ArrayAdapter<String> = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_item,
            yearList
        )
        aYears.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        yearSpinner.adapter = aYears

        val aGender: ArrayAdapter<String> = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_item,
            genders
        )
        aGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner.adapter = aGender

        var day: String?
        val dayList =  ArrayList<String>()
        days.forEach { d ->
            day = d.toString()
            day?.let { dayList.add(it) }
        }
        val aDay: ArrayAdapter<String> = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_item,
            dayList
        )
        aDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        daySpinner.adapter = aDay
    }

    var picBitmap: Bitmap? = null
    var chooseLogo = registerForActivityResult(GetContent()) { result: Uri? ->
        if (result != null) {
            try {
                picBitmap = decodeBitmapUri(requireActivity(), result)
                if (picBitmap!!.config == null) {
                    picBitmap = null
                } else {
                    val documentFile = DocumentFile.fromSingleUri(requireActivity(), result)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @Throws(FileNotFoundException::class)
    fun decodeBitmapUri(ctx: Context, uri: Uri?): Bitmap? {
        val targetW = 600
        val targetH = 600
        val bmOptions = BitmapFactory.Options()
        bmOptions.inJustDecodeBounds = true
        BitmapFactory.decodeStream(ctx.contentResolver.openInputStream(uri!!), null, bmOptions)
        val photoW = bmOptions.outWidth
        val photoH = bmOptions.outHeight
        val scaleFactor = (photoW / targetW).coerceAtMost(photoH / targetH)
        bmOptions.inJustDecodeBounds = false
        bmOptions.inSampleSize = scaleFactor
        return BitmapFactory.decodeStream(
            requireActivity().contentResolver.openInputStream(uri), null, bmOptions
        )
    }

}