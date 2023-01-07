package com.example.projectsertifkasijmp.ui.insert

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.projectsertifkasijmp.database.Student
import com.example.projectsertifkasijmp.repository.RegistrationRepository

class RegistraionAddUpdateViewModel(application: Application) : ViewModel() {
    private val registrationRepository: RegistrationRepository = RegistrationRepository(application)
    fun insert(student: Student) {
        registrationRepository.insert(student)
    }
    fun update(student: Student) {
        registrationRepository.update(student)
    }
    fun delete(student: Student) {
        registrationRepository.delete(student)
    }
}