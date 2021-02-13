package com.example.myapplication.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.db.DbHelper
import com.example.myapplication.model.Info
import com.example.myapplication.ui.personal_info.PersonalInfoActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var fabAdd: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        fabAdd = findViewById(R.id.fabAdd)
        fabAdd.setOnClickListener {
            startActivity(Intent(this, PersonalInfoActivity::class.java))
        }

        rvInfo.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        getJsonData()
    }

    private fun getJsonData() {
        val cursor = DbHelper.getJsonData()
        val list = ArrayList<Info>()
        while (cursor.moveToNext()) {
            val jsonData = cursor.getString(cursor.getColumnIndex("json_data"))
            val obj = JSONObject(jsonData)
            val info = Info(
                cursor.getString(cursor.getColumnIndex("id")),
                obj.getString("firstName"),
                obj.getString("lastName"),
                obj.getString("phone"),
                obj.getString("gender"),
                obj.getString("dob"),
                obj.getString("employeeNo"),
                obj.getString("designation"),
                obj.getString("accountType"),
                obj.getString("workExperience"),
                obj.getString("bankName"),
                obj.getString("branchName"),
                obj.getString("accountNo"),
                obj.getString("ifscCode"),
                obj.getString("imagePath"),
            )
            list.add(info)
        }
        cursor.close()
        rvInfo.adapter = InfoAdapter(list)
    }
}