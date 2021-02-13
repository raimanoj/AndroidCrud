package com.example.myapplication.ui.employee_info

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.model.Info
import com.example.myapplication.ui.bank_info.BankInfoActivity
import com.example.myapplication.utils.showToast

class EmployeeInfoActivity : AppCompatActivity() {

    lateinit var edtEmpNo: EditText
    lateinit var edtDesignation: EditText
    lateinit var spinnerAccountType: Spinner
    lateinit var spinnerWorkExp: Spinner
    var arrayAccountType = arrayOf("Saving", "Current")
    var arrayExperience = arrayOf("1 year", "2 years", "3 years", "4 years")
    var textAccountType: String? = null
    var textWorkExp: String? = null
    var info: Info? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_info)
        init()
    }

    private fun init() {
        val firstName = intent.getStringExtra("firstName")
        val lastName = intent.getStringExtra("lastName")
        val dob = intent.getStringExtra("dob")
        val phone = intent.getStringExtra("phone")
        val gender = intent.getStringExtra("gender")
        val infoSerializable = intent.getSerializableExtra("info")

        edtEmpNo = findViewById(R.id.edtEmpNo)
        edtDesignation = findViewById(R.id.edtDesignation)
        spinnerAccountType = findViewById(R.id.spinnerAccountType)
        spinnerWorkExp = findViewById(R.id.spinnerWorkExp)

        val aaAccountType: ArrayAdapter<*> =
            ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_item, arrayAccountType)
        val aaWorkExp: ArrayAdapter<*> =
            ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_item, arrayExperience)
        aaAccountType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        aaWorkExp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerAccountType.adapter = aaAccountType
        spinnerWorkExp.adapter = aaWorkExp

        spinnerAccountType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                textAccountType = arrayAccountType[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        spinnerWorkExp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                textWorkExp = arrayExperience[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        if (infoSerializable != null) {
            info = infoSerializable as Info
            edtEmpNo.setText(info!!.empNo)
            edtDesignation.setText(info!!.designation)
            for ((i, value) in arrayAccountType.withIndex()) {
                if (value == info!!.accountType) {
                    spinnerAccountType.setSelection(i)
                }
            }

            for ((i, value) in arrayExperience.withIndex()) {
                if (value == info!!.workExp) {
                    spinnerWorkExp.setSelection(i)
                }
            }
        }

        findViewById<Button>(R.id.btnSubmit).setOnClickListener {
            if (edtEmpNo.text.isEmpty() ||
                edtDesignation.text.isEmpty()
            ) {
                showToast("Enter all Fields!!")
                return@setOnClickListener
            }
            Intent(this, BankInfoActivity::class.java).apply {
                putExtra("firstName", firstName)
                putExtra("lastName", lastName)
                putExtra("dob", dob)
                putExtra("phone", phone)
                putExtra("gender", gender)
                putExtra("employeeNo", edtEmpNo.text.toString())
                putExtra("designation", edtDesignation.text.toString())
                putExtra("accountType", textAccountType)
                putExtra("workExperience", textWorkExp)
                putExtra("info", info)
                startActivity(this)
            }
        }

    }
}