package com.example.projectsertifkasijmp.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.projectsertifkasijmp.database.Student
import com.example.projectsertifkasijmp.repository.RegistrationRepository

class MainViewModel(application: Application) : ViewModel() {
    private val registrationRepository: RegistrationRepository = RegistrationRepository(application)

    fun getAllRegistration(): LiveData<List<Student>> = registrationRepository.getAllRegistration()

}