package com.example.myapplication.ui.personal_info

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.myapplication.R
import com.example.myapplication.model.Info
import com.example.myapplication.ui.employee_info.EmployeeInfoActivity
import com.example.myapplication.utils.showToast
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_personal_info.view.*
import java.text.SimpleDateFormat
import java.util.*

class PersonalInfoActivity : AppCompatActivity() {
    lateinit var radioGroup: RadioGroup
    lateinit var genderRadioButton: RadioButton
    lateinit var edtFirstName: EditText
    lateinit var edtLastName: EditText
    lateinit var edtPhone: EditText
    lateinit var edtDob: EditText
    var info: Info? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_info)
        init()
    }

    private fun init() {
        radioGroup = findViewById(R.id.radioGroup)
        edtFirstName = findViewById(R.id.edtFirstName)
        edtLastName = findViewById(R.id.edtLastName)
        edtPhone = findViewById(R.id.edtPhone)
        edtDob = findViewById(R.id.edtDob)

        val infoSerializable = intent.getSerializableExtra("info")
        if (infoSerializable != null) {
            info = infoSerializable as Info
            edtFirstName.setText(info!!.firstName)
            edtLastName.setText(info!!.lastName)
            edtPhone.setText(info!!.phone)
            edtDob.setText(info!!.dob)
            if (info!!.gender == "Male") {
                radioGroup.radioMale.isChecked = true
            } else {
                radioGroup.radioFemale.isChecked = true
            }
        }

        edtDob.setOnClickListener {
            val builder = MaterialDatePicker.Builder.datePicker()
            val picker = builder.build()
            picker.show(supportFragmentManager, "DATE_PICKER")
            picker.addOnPositiveButtonClickListener {
                edtDob.setText(getDate(it))
            }
        }

        findViewById<Button>(R.id.btnSubmit).setOnClickListener {
            val selectedId = radioGroup.checkedRadioButtonId
            if (edtFirstName.text.toString().isEmpty() ||
                edtLastName.text.isEmpty() ||
                edtPhone.text.isEmpty() ||
                selectedId == -1
            ) {
                showToast("Enter all fields!")
                return@setOnClickListener
            }
            genderRadioButton = findViewById(selectedId)
            Intent(this, EmployeeInfoActivity::class.java).apply {
                putExtra("firstName", edtFirstName.text.toString())
                putExtra("lastName", edtLastName.text.toString())
                putExtra("dob", edtDob.text.toString())
                putExtra("phone", edtPhone.text.toString())
                putExtra("gender", genderRadioButton.text.toString())
                putExtra("info", info)
                startActivity(this)
            }

        }

    }

    private fun getDate(long: Long): String? {
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
        return sdf.format(Date(long))
    }

}