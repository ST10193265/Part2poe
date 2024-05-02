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
import android.widget.CalendarView
import androidx.activity.result.contract.ActivityResultContracts
import com.example.part2poe.databinding.FragmentAddtimelogBinding
import com.example.part2poe.ui.GlobalVar
import com.example.part2poe.ui.main_project.MainProjectFragmentDirections
import java.util.Calendar
import java.util.Date


class AddTimeLogFragment : Fragment() {
    private var _binding: FragmentAddtimelogBinding? = null
    private val binding get() = _binding!!
    //binds the layout fragment
    private var startTime: Long? = null
    private val addTimeLogViewModel: AddTimeLogViewModel by lazy {
        ViewModelProvider(requireActivity()).get(addTimeLogViewModel::class.java)
    }
    // this method was adapted from android developer
    // https://developer.android.com/topic/libraries/view-binding


    // method to allow user to add an image to the time log
    private val pickImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val selectedImageUri = data?.data
            Toast.makeText(requireContext(), "Image selected: $selectedImageUri", Toast.LENGTH_SHORT).show()
        }

        // this method was adapted from geeksfor geeks
        // https://www.geeksforgeeks.org/photo-picker-in-android-13-with-example-project/
        // chaitanyamunje
        // https://auth.geeksforgeeks.org/user/chaitanyamunje/articles?utm_source=geeksforgeeks&utm_medium=article_author&utm_campaign=auth_user

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

        // this method was adapted from android developer
        // https://developer.android.com/topic/libraries/view-binding

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

        // Assuming your CalendarView has the ID 'calendarView' in your layout
        val calendarView = binding.calendarView

         // Variable to hold the selected date as a Date object
        var calendarDate: Date? = null

          // Set the OnDateChangeListener to the CalendarView
        calendarView.setOnDateChangeListener { view: CalendarView, year: Int, month: Int, dayOfMonth: Int ->
            // Create a Calendar instance to convert the selected date to a Date object
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)

            // Assign the selected date to 'calendarDate'
            calendarDate = calendar.time
        }


        // method for save
        btnSave.setOnClickListener {

            // storing user entered and selected values
            val description = editDescription.text.toString().trim()
            val project = editProject.selectedItem.toString()
            val calender = calendarDate

            // validates the data eneterd then adds it to the project class
            if (startTime != null && isValidInput(description, project, calender)) {
                addNewTimeLog(description, startTime!!,calender, project)

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

        // the method above were adapted android
        // https://kotlinandroid.org/button/setonclicklistener/#:~:text=Steps%20to%20call%20setOnClickListener%20%28%29%20on%20Button%201,executed%20when%20a%20tap%20happens%20on%20the%20button.

        return root
    }


    // method to check for valid fields
    private fun isValidInput(

        description: String,
        project: String,
        calendar: Date?
    ): Boolean {
        return  description.isNotEmpty()  && calendar.toString().isNotEmpty() && project.isNotEmpty()
    }

// method for adding a new time log
    private fun addNewTimeLog(

    description: String,
    startTime: Long,
    calendar: Date?,
    project: String,
    endTime: Long? = null,
    imageUri: Uri? = null
    ) {
        val timelog = TimeLog(description, startTime, endTime, calendar, project, imageUri)
        GlobalVar.GlobalVariables.oagTimeLog.add(timelog)

        Toast.makeText(requireContext(), "New time log added successfully!", Toast.LENGTH_SHORT).show()

    // this method was adapted in geeksforgeeks
    // https://www.geeksforgeeks.org/android-toast-in-kotlin/
    // SriHarshaBammidi
    // https://auth.geeksforgeeks.org/user/SriHarshaBammidi/articles?utm_source=geeksforgeeks&utm_medium=article_author&utm_campaign=auth_user

    }

    private fun clearFields(vararg editTexts: EditText) {
        for (editText in editTexts) {
            editText.text.clear()
        }
    }
    // clears feilds
    // this method was adapted from techiedelight
    // https://www.techiedelight.com/clear-list-in-kotlin/#:~:text=Clear%20a%20List%20in%20Kotlin%201%201.%20Using,remove%28%29%20function%20...%204%204.%20Using%20Iterator.remove%28%29%20function
    // vivek-srivastava
    // https://www.techiedelight.com/vivek-srivastava/

    private fun navigateToMainTimeLog() {
        findNavController().navigate(MainProjectFragmentDirections.actionAddtimelogFragmentToMaintimelogFragment())
    }
    //navigates to main time log
    // this method has been adapted from android developer
    // https://developer.android.com/guide/navigation/navcontroller

    // opens image in gallery method
    private fun openImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        pickImage.launch(intent)
    }

    // this method was adapted from geeksfor geeks
    // https://www.geeksforgeeks.org/photo-picker-in-android-13-with-example-project/
    // chaitanyamunje
    // https://auth.geeksforgeeks.org/user/chaitanyamunje/articles?utm_source=geeksforgeeks&utm_medium=article_author&utm_campaign=auth_user

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




