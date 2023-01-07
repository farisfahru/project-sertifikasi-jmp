package com.example.projectsertifkasijmp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectsertifkasijmp.adapter.RegistrationAdapter.RegistrationViewHolder
import com.example.projectsertifkasijmp.database.Student
import com.example.projectsertifkasijmp.databinding.ItemRegistrationBinding
import com.example.projectsertifkasijmp.helper.RegistrationDiffCallback
import com.example.projectsertifkasijmp.ui.insert.RegistrationAddUpdateActivity

class RegistrationAdapter : RecyclerView.Adapter<RegistrationViewHolder>() {
    private val listRegistration = ArrayList<Student>()
    fun setListRegistration(listRegistration: List<Student>) {
        val diffCallback = RegistrationDiffCallback(this.listRegistration, listRegistration)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listRegistration.clear()
        this.listRegistration.addAll(listRegistration)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class RegistrationViewHolder(private val binding: ItemRegistrationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(student: Student) {
            with(binding) {
                tvItemDate.text = student.date
                tvItemName.text = student.name
                tvItemAddress.text = student.address
                tvItemPhone.text = student.phone
                tvItemGender.text = student.gender
                tvItemLocation.text = student.location
                Glide.with(itemView)
                    .load(student.image)
                    .into(ivPhoto)
                cvItemRegistration.setOnClickListener {
                    val intent = Intent(it.context, RegistrationAddUpdateActivity::class.java)
                    intent.putExtra(RegistrationAddUpdateActivity.EXTRA_REGISTRATION, student)
                    it.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegistrationViewHolder {
        val binding =
            ItemRegistrationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RegistrationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RegistrationViewHolder, position: Int) {
        holder.bind(listRegistration[position])
    }

    override fun getItemCount(): Int = listRegistration.size
}