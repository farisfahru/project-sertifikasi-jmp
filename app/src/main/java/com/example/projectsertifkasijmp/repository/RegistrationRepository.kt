package com.example.projectsertifkasijmp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.projectsertifkasijmp.database.RegistrationDao
import com.example.projectsertifkasijmp.database.RegistrationDatabase
import com.example.projectsertifkasijmp.database.Student
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class RegistrationRepository(application: Application) {
    private val registrationDao: RegistrationDao
    private val executorService : ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = RegistrationDatabase.getDatabase(application)
        registrationDao = db.registrationDao()
    }
    fun insert(student: Student) {
        executorService.execute{registrationDao.insert(student)}
    }
    fun delete(student: Student) {
        executorService.execute{registrationDao.delete(student)}
    }
    fun update(student: Student) {
        executorService.execute{registrationDao.update(student)}
    }
    fun getAllRegistration() : LiveData<List<Student>> = registrationDao.getAllRegistration()
}
