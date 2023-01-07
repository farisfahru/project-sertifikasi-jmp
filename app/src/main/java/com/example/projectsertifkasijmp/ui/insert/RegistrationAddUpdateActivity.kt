package com.example.projectsertifkasijmp.ui.insert

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.projectsertifkasijmp.R
import com.example.projectsertifkasijmp.R.id.radio_group
import com.example.projectsertifkasijmp.database.Student
import com.example.projectsertifkasijmp.databinding.ActivityRegistrationAddUpdateBinding
import com.example.projectsertifkasijmp.helper.DateHelper
import com.example.projectsertifkasijmp.helper.ViewModelFactory
import com.example.projectsertifkasijmp.ui.maps.MapsActivity
import com.example.projectsertifkasijmp.utils.Utils
import java.io.File

class RegistrationAddUpdateActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_REGISTRATION = "extra_registraion"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }
    private var _binding: ActivityRegistrationAddUpdateBinding? = null
    private val binding get() = _binding
    private var getFile: File? = null
    private var isEdit = false
    private var gender: String? = null
    private var student: Student? = null
    private lateinit var registraionAddUpdateViewModel: RegistraionAddUpdateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegistrationAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        registraionAddUpdateViewModel = obtainViewModel(this@RegistrationAddUpdateActivity)

        onActions()

        binding?.btnUpload?.setOnClickListener { startGallery() }

    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if (it.resultCode == MapsActivity.RESULT_CODE && it.data != null) {
            val selectValue = it?.data?.getStringExtra(MapsActivity.EXTRA_SELECTED_VALUE)
            binding?.tvResultLocation?.text = selectValue.toString()
        }
    }

    private fun onActions() {
        binding?.btnMaps?.setOnClickListener {
            val intent = Intent(this@RegistrationAddUpdateActivity, MapsActivity::class.java)
            resultLauncher.launch(intent)
            println("Hasil: $resultLauncher")
        }

        student = intent.getParcelableExtra(EXTRA_REGISTRATION)

        if (student != null) {
            isEdit = true
        } else {
            student = Student()
        }

        var actionBarTitle: String
        var btnTitle: String

        if (isEdit) {
            actionBarTitle = getString(R.string.change)
            btnTitle = getString(R.string.update)
            if (student != null) {
                student?.let { student ->
                    binding?.edtName?.setText(student.name)
                    binding?.edtAddress?.setText(student.address)
                    binding?.edtPhone?.setText(student.phone)
                }
            } else {
                actionBarTitle = getString(R.string.add)
                btnTitle = getString(R.string.save)
            }
        } else {
            actionBarTitle = getString(R.string.add)
            btnTitle = getString(R.string.save)
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding?.btnSubmit?.text = btnTitle
        binding?.btnSubmit?.setOnClickListener {
            val name = binding?.edtName?.text.toString().trim()
            val address = binding?.edtAddress?.text.toString().trim()
            val phone = binding?.edtPhone?.text.toString().trim()
            val location = binding?.tvResultLocation?.text.toString().trim()
            val radioButton = binding?.radioGroup
            if (radio_group > 0) {
                when (radioButton?.checkedRadioButtonId) {
                    R.id.radio_pria -> gender = "Pria"
                    R.id.radio_wanita -> gender = "Wanita"
                }
            }
            when {
                name.isEmpty() -> {
                    binding?.edtName?.error = getString(R.string.empty)
                }
                address.isEmpty() -> {
                    binding?.edtAddress?.error = getString(R.string.empty)
                }
                phone.isEmpty() -> {
                    binding?.edtPhone?.error = getString(R.string.empty)
                }
                location.isEmpty() -> {
                    binding?.tvResultLocation?.error = getString(R.string.empty)
                }
                else -> {
                    student.let { student ->
                        student?.name = name
                        student?.address = address
                        student?.phone = phone
                        student?.location = location
                        student?.image = getFile.toString()
                        student?.gender = gender.toString()
                    }
                    if (isEdit) {
                        registraionAddUpdateViewModel.update(student as Student)
                        showToast(getString(R.string.changed))
                    } else {
                        student.let { student ->
                            student?.date = DateHelper.getCurrentDate()
                        }
                        registraionAddUpdateViewModel.insert(student as Student)
                        showToast(getString(R.string.added))
                    }
                    finish()
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            val selectedImg: Uri = it.data?.data!!
            val myFile = Utils.uriToFile(selectedImg, this@RegistrationAddUpdateActivity)
            getFile = myFile
            val data = Uri.fromFile(myFile)
            binding?.ivPreview?.setImageURI(data)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_form, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE)
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(R.string.message_cancel)
        } else {
            dialogMessage = getString(R.string.message_delete)
            dialogTitle = getString(R.string.delete)
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                if (!isDialogClose) {
                    registraionAddUpdateViewModel.delete(student as Student)
                    showToast(getString(R.string.deleted))
                }
                finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun obtainViewModel(activity: AppCompatActivity): RegistraionAddUpdateViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[RegistraionAddUpdateViewModel::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}