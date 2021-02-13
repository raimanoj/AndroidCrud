package com.example.myapplication.model

import java.io.Serializable

data class Info(
    val id: String,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val gender: String,
    val dob: String,
    val empNo: String,
    val designation: String,
    val accountType: String,
    val workExp: String,
    val bankName: String,
    val branchName: String,
    val accountNo: String,
    val ifscCode: String,
    val imagePAth: String
) : Serializable