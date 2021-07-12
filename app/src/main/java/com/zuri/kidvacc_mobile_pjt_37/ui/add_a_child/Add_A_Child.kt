package com.zuri.kidvacc_mobile_pjt_37.ui.add_a_child

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.appcompat.widget.AppCompatSpinner
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.request.SimpleMultiPartRequest
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.zuri.kidvacc_mobile_pjt_37.R
import com.zuri.kidvacc_mobile_pjt_37.networking.VolleyAuth
import com.zuri.kidvacc_mobile_pjt_37.networking.VolleySingleton
import java.io.FileNotFoundException
import java.util.*
import kotlin.collections.ArrayList


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

       /* root.findViewById<Button>(R.id.add_new_child).setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().remove(this@Add_A_Child).commit();
        }*/

        root.findViewById<ImageView>(R.id.add_a_child_photo).setOnClickListener{
            chooseLogo.launch("image/*")
        }

        childPicturePreview.setOnClickListener {
            chooseLogo.launch("image/*")
        }
        setupViews(root)
        return root
    }

    private fun setupViews(root: View){
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
        val vaccines = arrayOf(
            "None",
            "BCG (Tubercolosis)",
            "Whopping Cough",
            "Measles",
            "Hepatitis B",
            "Polio (POV)",
            "Yellow Fever",
            "Polio(IPV)",
            "Pneumococcal (PCV)",
            "Rotavirus (RV)",
            "Hepatitis B",
            "Diphtheria, tetanus, and whooping cough (pertussis) (DTaP)",
            "Haemophilus influenzae type b (Hib)"
        )

        val daySpinner = root.findViewById<AppCompatSpinner>(R.id.day_spinner)
        val monthSpinner = root.findViewById<AppCompatSpinner>(R.id.month_spinner)
        val yearSpinner = root.findViewById<AppCompatSpinner>(R.id.year_spinner)
        val genderSpinner = root.findViewById<AppCompatSpinner>(R.id.gender_spinner)
        val bloodGroupSpinner = root.findViewById<AppCompatSpinner>(R.id.blood_group_spinner)
        val genotypeSpinner = root.findViewById<AppCompatSpinner>(R.id.genotype_spinner)
        val vaccinesSpinner = root.findViewById<AppCompatSpinner>(R.id.vaccines_spinner)
        val firstName = root.findViewById<TextView>(R.id.first_name)
        val surname = root.findViewById<TextView>(R.id.surname)
        val healthConditions = root.findViewById<TextView>(R.id.other_editText)
        val addNewChild = root.findViewById<MaterialButton>(R.id.add_new_child)

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

        val aVaccines: ArrayAdapter<String> = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_item,
            vaccines
        )
        aVaccines.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        vaccinesSpinner.adapter = aVaccines

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

        addNewChild.setOnClickListener {
            val firstNameString = firstName.text.toString()
            val lastNameString = surname.text.toString()
            val genderString = genderSpinner.selectedItem.toString()
            val bloodGroupString = bloodGroupSpinner.selectedItem.toString()
            val genotypeString = genotypeSpinner.selectedItem.toString()
            val vaccinationHistory = vaccinesSpinner.selectedItem.toString() +" /n"+ healthConditions.text.toString()

            /*addNewChild(
                firstNameString,
                lastNameString,
                genderString,
                bloodGroupString,
                genotypeString,
                vaccinationHistory
            )*/
            requireActivity().supportFragmentManager.beginTransaction().remove(this@Add_A_Child).commit();
        }
    }

    var picBitmap: Bitmap? = null
    var documentFile: DocumentFile? = null
    private var chooseLogo = registerForActivityResult(GetContent()) { result: Uri? ->
        if (result != null) {
            try {
                picBitmap = decodeBitmapUri(requireActivity(), result)
                if (picBitmap!!.config == null) {
                    picBitmap = null
                } else {
                    documentFile = DocumentFile.fromSingleUri(requireActivity(), result)
                    childPicturePreview.setImageBitmap(picBitmap)
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

    fun addNewChild(
        firstName: String,
        lastName: String,
        gender: String,
        bloodGroup: String,
        genotype: String,
        vaccinationHistory: String
    ){
        val progressDialog = ProgressDialog(requireActivity())
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Adding Child")
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()

        val headers: HashMap<String, String> = HashMap<String, String>()
        headers["Authorization"] = "Token "+ VolleyAuth.TOKEN

        val request = SimpleMultiPartRequest(
            Request.Method.POST,
            VolleyAuth.URL_CHILD_LIST,
            { response ->
                Log.i("Response", response)
                progressDialog.dismiss()
            },
            { error ->
                error.printStackTrace()
                progressDialog.dismiss()
            })

        request.addFile("images", documentFile?.uri?.path.toString())
        request.headers = headers
        request.addMultipartParam("First_name", "text/plain", firstName)
        request.addMultipartParam("Middle_name", "text/plain", "")
        request.addMultipartParam("Last_name", "text/plain", lastName)
        request.addMultipartParam("Gender", "text/plain", gender)
        request.addMultipartParam("Blood_group", "text/plain", bloodGroup)
        request.addMultipartParam("Genotype", "text/plain", genotype)
        request.addMultipartParam("Vaccination_history", "text/plain", vaccinationHistory)

        VolleySingleton.getInstance(requireActivity()).addToRequestQueue(request)
    }
}