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
import com.example.part2poe.databinding.FragmentAddProjectBinding
import com.example.part2poe.ui.GlobalVar
import com.example.part2poe.ui.main_project.MainProjectFragmentDirections



class AddProjectFragment : Fragment() {
    private var _binding: FragmentAddProjectBinding? = null
    private val binding get() = _binding!!

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
        val testproject = binding.textView3
        val btnSave = binding.btnSave
        val btnCancel = binding.btnCancel

        // Create a list of category names
       val categoryNames = GlobalVar.GlobalVariables.oagCategory.map { it.categoryname }

        // Create an adapter using the category names and a default spinner layout
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoryNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        editCategory.adapter = adapter

        btnSave.setOnClickListener {
            val projectName = editProjectName.text.toString().trim()
            val minGoal = editMinGoal.text.toString().trim()
            val maxGoal = editMaxGoal.text.toString().trim()
            val cost = editCost.text.toString().trim()

            val category = editCategory.selectedItem.toString()
            val calendar = editCalendar.date.toString()

            if (isValidInput(projectName, minGoal, maxGoal, cost, calendar)) {
                addNewProject(projectName, minGoal, maxGoal, cost, calendar, category)
                testproject.text = buildProjectString(projectName, minGoal, maxGoal, cost, calendar, category)
            } else {
                Toast.makeText(requireContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show()
            }


           // navigateToMainProject()

            clearFields(editProjectName, editMinGoal, editMaxGoal, editCost)
        }

        btnCancel.setOnClickListener {
            clearFields(editProjectName, editMinGoal, editMaxGoal, editCost)
        }
        return root
    }


    private fun isValidInput(
        projectName: String,
        minGoal: String,
        maxGoal: String,
        cost: String,
        calendar: String
    ): Boolean {
        return projectName.isNotEmpty() && minGoal.isNotEmpty() && maxGoal.isNotEmpty() && cost.isNotEmpty() && calendar.isNotEmpty()
    }

    private fun addNewProject(
        projectName: String,
        minGoal: String,
        maxGoal: String,
        cost: String,
        calendar: String,
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

    private fun navigateToMainProject() {
        findNavController().navigate(MainProjectFragmentDirections.actionAddProjectFragmentToMainProjectFragment())
    }

    private fun buildProjectString(
        projectName: String,
        minGoal: String,
        maxGoal: String,
        cost: String,
        calendar: String,
        category: String
    ): String {
        return "Project Name: $projectName\n" +
                "Minimum Goal: $minGoal\n" +
                "Maximum Goal: $maxGoal\n" +
                "Cost: $cost\n" +
                "Calendar: $calendar\n" +
                "Category: $category"
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}