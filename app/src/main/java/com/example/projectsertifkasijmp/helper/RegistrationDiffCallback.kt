package com.example.projectsertifkasijmp.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.projectsertifkasijmp.database.Student

class RegistrationDiffCallback(
    private val oldRegistrationList: List<Student>,
    private val newRegistrationList: List<Student>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldRegistrationList.size
    }

    override fun getNewListSize(): Int {
        return newRegistrationList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldRegistrationList[oldItemPosition].id == newRegistrationList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = oldRegistrationList[oldItemPosition]
        val newEmployee = newRegistrationList[newItemPosition]
        return oldEmployee.name == newEmployee.name && oldEmployee.address == newEmployee.address
    }

}