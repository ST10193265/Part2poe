package com.example.part2poe.ui.add_project

import androidx.fragment.app.viewModels
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
import com.example.part2poe.databinding.FragmentAddProjectBinding
import com.example.part2poe.ui.GlobalVar
import com.example.part2poe.ui.main_project.MainProjectFragmentDirections
import java.util.Calendar
import java.util.Date


class AddProjectFragment : Fragment() {
    private var _binding: FragmentAddProjectBinding? = null
    private val binding get() = _binding!!
    //binds the layout fragment

    private val addProjectViewModel: AddProjectViewModel by lazy {
        ViewModelProvider(requireActivity()).get(AddProjectViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddProjectBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val editProjectName = binding.etxtProjecName
        val editMinGoal = binding.etxtMinGoal
        val editMaxGoal = binding.etxtMaxGoal
        val editCost = binding.etxtCost
        val editCategory = binding.dpCategory
        val editCalendar = binding.calendarView
        val btnSave = binding.btnSave
        val btnCancel = binding.btnCancel
        // binds all the feilds

        // Create a list of category names
       val categoryNames = GlobalVar.GlobalVariables.oagCategory.map { it.categoryname }

        // Create an adapter using the category names and a default spinner layout
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoryNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        editCategory.adapter = adapter

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

        btnSave.setOnClickListener {
            val projectName = editProjectName.text.toString().trim()
            val minGoal = editMinGoal.text.toString().trim()
            val maxGoal = editMaxGoal.text.toString().trim()
            val cost = editCost.text.toString().trim()

            val category = editCategory.selectedItem.toString()
            val calender = calendarDate

            if (isValidInput(projectName, minGoal, maxGoal, cost, calender)) {
                addNewProject(projectName, minGoal, maxGoal, cost, calender, category)
                // validates the data eneterd then adds it to the project class
            } else {
                Toast.makeText(requireContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show()
            }


           navigateToMainProject()
           clearFields(editProjectName, editMinGoal, editMaxGoal, editCost)
            //clears feilds and then navigates to main project
        }

        btnCancel.setOnClickListener {
            clearFields(editProjectName, editMinGoal, editMaxGoal, editCost)
            // clears feilds
        }
        return root
    }


    private fun isValidInput(
        projectName: String,
        minGoal: String,
        maxGoal: String,
        cost: String,
        calendar: Date?
    ): Boolean {
        return projectName.isNotEmpty() && minGoal.isNotEmpty() && maxGoal.isNotEmpty() && cost.isNotEmpty() && calendar.toString().isNotEmpty()
    }
    // validates feilds

    private fun addNewProject(
        projectName: String,
        minGoal: String,
        maxGoal: String,
        cost: String,
        calendar: Date?,
        category: String
    ) {
        val project = Project(projectName, minGoal, maxGoal, cost, calendar, category)
        GlobalVar.GlobalVariables.oagProject.add(project)

        Toast.makeText(requireContext(), "New project added successfully!", Toast.LENGTH_SHORT).show()
    }

    private fun clearFields(vararg editTexts: EditText) {
        for (editText in editTexts) {
            editText.text.clear()
        }
    }
    // clears feilds


    private fun navigateToMainProject() {
        findNavController().navigate(MainProjectFragmentDirections.actionAddProjectFragmentToMainProjectFragment())
    }
    //navigates to main project


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}