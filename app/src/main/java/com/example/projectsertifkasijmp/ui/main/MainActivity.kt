package com.example.projectsertifkasijmp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectsertifkasijmp.R
import com.example.projectsertifkasijmp.adapter.RegistrationAdapter
import com.example.projectsertifkasijmp.databinding.ActivityMainBinding
import com.example.projectsertifkasijmp.helper.ViewModelFactory
import com.example.projectsertifkasijmp.ui.insert.RegistrationAddUpdateActivity

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding

    private lateinit var adapter: RegistrationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        onAction()
    }

    private fun onAction() {
        val mainViewModel = obtainViewModel(this@MainActivity)
        mainViewModel.getAllRegistration().observe(this) { registrationList ->
            if (registrationList != null) {
                adapter.setListRegistration(registrationList)
            }
        }

        adapter = RegistrationAdapter()
        binding?.rvRegistration?.layoutManager = LinearLayoutManager(this)
        binding?.rvRegistration?.setHasFixedSize(true)
        binding?.rvRegistration?.adapter = adapter

        binding?.fabAdd?.setOnClickListener {view ->
            if (view.id == R.id.fab_add) {
                val intent = Intent(this@MainActivity, RegistrationAddUpdateActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity) : MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[MainViewModel::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}