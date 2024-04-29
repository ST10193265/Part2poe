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

    private val addTimeLogViewModel: AddTimeLogViewModel by lazy {
        ViewModelProvider(requireActivity()).get(addTimeLogViewModel::class.java)
    }

    private val pickImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            // Handle the selected image here, you may want to display it or save its URI
            // For example:
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

        val editDescription = binding.etxtDescription
        val editStartTime = binding.etxtStartTime
        val editProject = binding.dpProject
        val editCalendar = binding.calendarView
        val btnSave = binding.btnSave
        val btnCancel = binding.btnCancel
        val btnAddPhoto = binding.btnAddphoto
        // binds all the feilds

        // Create a list of PROJECT names
        val projectNames = GlobalVar.GlobalVariables.oagProject.map { it.projectName }

        // Create an adapter using the project names and a default spinner layout
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, projectNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        editProject.adapter = adapter

        btnSave.setOnClickListener {

            val description = editDescription.text.toString().trim()
            val startTime = editStartTime.text.toString().trim()
            val project = editProject.selectedItem.toString()
            val calender = editCalendar.date.toString()

            if (isValidInput(description, startTime, project, calender)) {
                addNewTimeLog(description, startTime, project, calender)
                // validates the data eneterd then adds it to the project class
            } else {
                Toast.makeText(requireContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show()
            }


            navigateToMainTimeLog()
            clearFields( editDescription, editStartTime)
            //clears feilds and then navigates to main time log
        }

        btnCancel.setOnClickListener {
            clearFields( editDescription, editStartTime)
            // clears feilds
        }

        btnAddPhoto.setOnClickListener {
            openImageGallery()
        }

        return root
    }


    private fun isValidInput(

        description: String,
        startTime: String,
        project: String,
        calendar: String
    ): Boolean {
        return  description.isNotEmpty() && startTime.isNotEmpty()  && calendar.isNotEmpty() && project.isNotEmpty()
    }
    // validates feilds

    private fun addNewTimeLog(

        description: String,
        startTime: String,
        calendar: String,
        project: String,
        imageUri: Uri? = null // Optional parameter for image URI
    ) {
        val timelog = TimeLog(description, startTime, calendar, project, imageUri)
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
    //navigates to main project

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




