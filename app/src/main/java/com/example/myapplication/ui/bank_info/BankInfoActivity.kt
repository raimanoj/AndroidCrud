package com.example.myapplication.ui.bank_info

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.db.DbHelper
import com.example.myapplication.model.Info
import com.example.myapplication.ui.main.MainActivity
import com.example.myapplication.utils.showToast
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream

class BankInfoActivity : AppCompatActivity() {
    lateinit var edtBankName: EditText
    lateinit var edtAccountNo: EditText
    lateinit var edtIfsc: EditText
    lateinit var ivImage: ImageView
    lateinit var spinnerBranch: Spinner
    var arrayBranches = arrayOf("Malad", "Kandivali", "Borivali")
    var textBranch = ""
    var imagePath = ""
    var info: Info? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_info)
        init()
    }

    private fun init() {
        val firstName = intent.getStringExtra("firstName")
        val lastName = intent.getStringExtra("lastName")
        val dob = intent.getStringExtra("dob")
        val phone = intent.getStringExtra("phone")
        val gender = intent.getStringExtra("gender")
        val employeeNo = intent.getStringExtra("employeeNo")
        val designation = intent.getStringExtra("designation")
        val accountType = intent.getStringExtra("accountType")
        val workExperience = intent.getStringExtra("workExperience")
        val infoSerializable = intent.getSerializableExtra("info")

        edtBankName = findViewById(R.id.edtBankName)
        edtAccountNo = findViewById(R.id.edtAccountNo)
        edtIfsc = findViewById(R.id.edtIfsc)
        ivImage = findViewById(R.id.ivImage)
        spinnerBranch = findViewById(R.id.spinnerBranch)

        val aaBranch: ArrayAdapter<*> =
            ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_item, arrayBranches)
        aaBranch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerBranch.adapter = aaBranch

        spinnerBranch.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                textBranch = arrayBranches[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        if (infoSerializable != null) {
            info = infoSerializable as Info
            edtBankName.setText(info!!.accountNo)
            edtIfsc.setText(info!!.ifscCode)
            edtAccountNo.setText(info!!.accountNo)
            imagePath = info!!.imagePAth
            Glide.with(this).load(info!!.imagePAth).into(ivImage)
            for ((i, value) in arrayBranches.withIndex()) {
                if (value == info!!.branchName) {
                    spinnerBranch.setSelection(i)
                }
            }
        }

        ivImage.setOnClickListener {
            openCamera()
        }
        findViewById<Button>(R.id.btnSubmit).setOnClickListener {
            if (edtBankName.text.isEmpty() ||
                edtAccountNo.text.isEmpty() ||
                edtIfsc.text.isEmpty() ||
                imagePath.isEmpty()
            ) {
                showToast("Enter All Fields!")
                return@setOnClickListener
            }

            val jsonObject = JSONObject().apply {
                put("firstName", firstName)
                put("lastName", lastName)
                put("dob", dob)
                put("phone", phone)
                put("gender", gender)
                put("employeeNo", employeeNo)
                put("designation", designation)
                put("accountType", accountType)
                put("workExperience", workExperience)
                put("bankName", edtBankName.text.toString())
                put("branchName", textBranch)
                put("accountNo", edtAccountNo.text.toString())
                put("ifscCode", edtIfsc.text.toString())
                put("imagePath", imagePath)
            }
            Log.d("TAGGGGGG", " ${jsonObject}")
            if (info != null) {
                DbHelper.updateJsonData(info!!.id, jsonObject.toString())
            } else {
                DbHelper.saveJsonData(jsonObject.toString())
            }
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

        }
    }


    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 1) {
            if (data != null) {
                imagePath = saveBitmap(data.extras!!.get("data") as Bitmap)
                Glide.with(this).load(imagePath).into(ivImage)
            }
        }
    }

    private fun saveBitmap(bitmap: Bitmap): String {
        val filePath =
            applicationInfo.dataDir + File.separator + "Images${System.currentTimeMillis()}"
        val file = File(filePath)
        val outPutStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outPutStream)
        return file.absolutePath
    }
}