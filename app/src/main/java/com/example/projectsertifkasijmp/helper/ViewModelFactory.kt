package com.example.projectsertifkasijmp.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projectsertifkasijmp.ui.insert.RegistraionAddUpdateViewModel
import com.example.projectsertifkasijmp.ui.main.MainViewModel

class ViewModelFactory constructor(private val application: Application) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application) as T
        } else if (modelClass.isAssignableFrom(RegistraionAddUpdateViewModel::class.java)) {
            return RegistraionAddUpdateViewModel(application) as T
        }
        throw IllegalAccessException("Unknown ViewModel class: ${modelClass.name}")
    }
}