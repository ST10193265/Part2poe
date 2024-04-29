package com.example.part2poe.ui.addtimelog

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import com.example.part2poe.databinding.FragmentAddtimelogBinding
import com.example.part2poe.ui.GlobalVar
import com.example.part2poe.ui.main_project.MainProjectFragmentDirections


class AddTimeLogFragment : Fragment() {
    private var _binding: FragmentAddtimelogBinding? = null
    private val binding get() = _binding!!
    //binds the layout fragment
    private var startTime: Long? = null
    private val addTimeLogViewModel: AddTimeLogViewModel by lazy {
        ViewModelProvider(requireActivity()).get(addTimeLogViewModel::class.java)
    }

    // method to allow user to add an image to the time log
    private val pickImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val selectedImageUri = data?.data
            Toast.makeText(requireContext(), "Image selected: $selectedImageUri", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddtimelogBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // binds all the feilds
        val editDescription = binding.etxtDescription
        val btnStartTimer = binding.btnStartTimer
        val editProject = binding.dpProject
        val editCalendar = binding.calendarView
        val btnSave = binding.btnSave
        val btnCancel = binding.btnCancel
        val btnAddPhoto = binding.btnAddphoto

        // Create a list of PROJECT names
        val projectNames = GlobalVar.GlobalVariables.oagProject.map { it.projectName }

        // Create an adapter using the project names and a default spinner layout
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, projectNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        editProject.adapter = adapter

        // method for starting timer
        btnStartTimer.setOnClickListener {
            startTime = System.currentTimeMillis()
            btnStartTimer.text = "Timer Started"
            btnStartTimer.isEnabled = false

        }

            // method for save
        btnSave.setOnClickListener {

            // storing user entered and selected values
            val description = editDescription.text.toString().trim()
            val project = editProject.selectedItem.toString()
            val calender = editCalendar.date.toString()

            // validates the data eneterd then adds it to the project class
            if (startTime != null && isValidInput(description, project, calender)) {
                addNewTimeLog(description, startTime!!, project, calender)

            } else {
                Toast.makeText(requireContext(), "Please fill in all the fields and/or start timer", Toast.LENGTH_SHORT).show()
            }


            navigateToMainTimeLog()
            clearFields( editDescription)
            //clears feilds and then navigates to main time log
        }

        btnCancel.setOnClickListener {
            clearFields( editDescription)
            // clears feilds
        }

        btnAddPhoto.setOnClickListener {
            openImageGallery()
            // opens gallery of user
        }

        return root
    }

// method to check for valid fields
    private fun isValidInput(

        description: String,
        project: String,
        calendar: String
    ): Boolean {
        return  description.isNotEmpty()  && calendar.isNotEmpty() && project.isNotEmpty()
    }

// method for adding a new time log
    private fun addNewTimeLog(

        description: String,
        startTime: Long,
        calendar: String,
        project: String,
        endTime: Long? = null,
        imageUri: Uri? = null
    ) {
        val timelog = TimeLog(description, startTime, endTime, calendar, project, imageUri)
        GlobalVar.GlobalVariables.oagTimeLog.add(timelog)

        Toast.makeText(requireContext(), "New time log added successfully!", Toast.LENGTH_SHORT).show()
    }

    private fun clearFields(vararg editTexts: EditText) {
        for (editText in editTexts) {
            editText.text.clear()
        }
    }
    // clears feilds


    private fun navigateToMainTimeLog() {
        findNavController().navigate(MainProjectFragmentDirections.actionAddtimelogFragmentToMaintimelogFragment())
    }
    //navigates to main time log

    // opens image in gallery method
    private fun openImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        pickImage.launch(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




