package com.example.myapplication.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.model.Info
import com.example.myapplication.ui.personal_info.PersonalInfoActivity
import kotlinx.android.synthetic.main.item_info.view.*

class InfoAdapter(private var list: ArrayList<Info>) :
    RecyclerView.Adapter<InfoAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val listItem =
            LayoutInflater.from(parent.context).inflate(R.layout.item_info, parent, false)
        return MyViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(info: Info) {
            Glide.with(itemView.context).load(info.imagePAth).into(itemView.ivImage)
            itemView.tvFirstName.text = info.firstName
            itemView.tvLastName.text = info.lastName
            itemView.tvPhone.text = info.phone
            itemView.tvGender.text = info.gender
            itemView.tvDob.text = info.dob
            itemView.tvEmpNo.text = info.empNo
            itemView.tvDesignation.text = info.designation
            itemView.tvAccountType.text = info.accountType
            itemView.tvWorkExp.text = info.workExp
            itemView.tvBankName.text = info.bankName
            itemView.tvBranchName.text = info.branchName
            itemView.tvAccountNo.text = info.accountNo
            itemView.tvIfsc.text = info.ifscCode

            itemView.setOnClickListener {
                Intent(itemView.context, PersonalInfoActivity::class.java).apply {
                    putExtra("info", info)
                    itemView.context.startActivity(this)
                }
            }
        }

    }
}