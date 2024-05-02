package com.example.part2poe.ui.main_project

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.ListView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.part2poe.R
import com.example.part2poe.databinding.FragmentMainCategoryBinding
import com.example.part2poe.databinding.FragmentMainProjectBinding
import com.example.part2poe.ui.GlobalVar
import com.example.part2poe.ui.main_category.MainCategoryFragmentDirections
import com.example.part2poe.ui.main_category.MainCategoryViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainProjectFragment : Fragment() {
    private var _binding: FragmentMainProjectBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainProjectBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val mainCategoryViewModel = ViewModelProvider(this).get(MainCategoryViewModel::class.java)

        // Find the Add New Project button and set a click listener
        val addProjectButton: Button = binding.btnAddNewProject
        addProjectButton.setOnClickListener {
            navigateToAddProject()
        }

        val Date: CheckBox = binding.cbDate
        Date.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                showDateRangePicker()
            } else {
                val originalList = GlobalVar.GlobalVariables.oagProject.map { it.projectName }

                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    originalList
                )
                binding.listProjects.adapter = adapter
            }
        }

        val homeButton: Button = binding.btnHome
        homeButton.setOnClickListener {
            findNavController().navigate(MainProjectFragmentDirections.actionMainProjectFragmentToHomeFragment())
        }

        val searchView: androidx.appcompat.widget.SearchView = binding.searchView
        val cbDate: CheckBox = binding.cbDate
        val listProjects: ListView = binding.listProjects

        // Initialize the list adapter for the ListView

        val projectEntries = GlobalVar.GlobalVariables.oagProject.map { it.projectName }
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, projectEntries)
        listProjects.adapter = adapter

        // Set up the SearchView to filter the list based on the project name and date
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                query?.let { filterProjects(it) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // As the user types, filter the list
                newText?.let { filterProjects(it) }
                return false
            }
        })

        return root
    }

    private fun filterProjects(query: String) {
        // Filter the time logs based on the project name
        val filteredList = GlobalVar.GlobalVariables.oagTimeLog.filter { timeLog ->
            timeLog.description.contains(query, ignoreCase = true)
        }.map { it.description }

        // Update the ListView adapter with the filtered list
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, filteredList)
        binding.listProjects.adapter = adapter
    }
    private fun showDateRangePicker() {
        val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker().apply {
            setTitleText("Select dates")
            setTheme(R.style.CustomMaterialCalendarStyle)
        }.build()

        dateRangePicker.addOnPositiveButtonClickListener { dateRange ->
            val startDate = Date(dateRange.first)
            val endDate = Date(dateRange.second)
            // Use startDate and endDate to filter your list view
            filterProjectByDate(startDate, endDate)
        }

        dateRangePicker.show(parentFragmentManager, dateRangePicker.toString())
    }
    private fun filterProjectByDate(startDate: Date, endDate: Date) {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        // Filter the time logs based on the date range
        val filteredList = GlobalVar.GlobalVariables.oagProject.filter { project ->
            val logDate =project.calendar
            logDate != null && logDate >= startDate && logDate <= endDate
        }.map { project ->
            // Map each timeLog to its description or another appropriate representation
            project.projectName
        }

        // Update the ListView adapter with the filtered list
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, filteredList)
        binding.listProjects.adapter = adapter
    }

    private fun navigateToAddProject() {
        findNavController().navigate(MainProjectFragmentDirections.actionMainProjectFragmentToAddProjectFragment())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}