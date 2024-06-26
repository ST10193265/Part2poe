package com.example.part2poe.ui.invoice

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.part2poe.databinding.FragmentInvoiceBinding
import com.example.part2poe.ui.GlobalVar
import com.example.part2poe.ui.main_category.MainCategoryFragmentDirections

class InvoiceFragment: Fragment() {
    private var _binding: FragmentInvoiceBinding? = null
    private val binding get() = _binding!!

    // this method was adapted from android developer
    // https://developer.android.com/topic/libraries/view-binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInvoiceBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val dpProject = binding.dpProject
        val etxtTrackedHours = binding.etxtTrackedHours
        val etxtCost = binding.etxtCost

        // this method was adapted from android developer
        // https://developer.android.com/topic/libraries/view-binding

        val projectNames = GlobalVar.GlobalVariables.oagProject.map { it.projectName }
        val projectAdapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, projectNames)
        projectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dpProject.adapter = projectAdapter

        val homeButton: Button = binding.btnHome
        homeButton.setOnClickListener {
            findNavController().navigate(InvoiceFragmentDirections.actionInvoiceFragmentToHomeFragment())
        }

        // method for the selected item listener
        dpProject.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedProject = GlobalVar.GlobalVariables.oagProject[position]
                val TimeLog = GlobalVar.GlobalVariables.oagTimeLog[position]
                val totalHours = calculateTotalHours(TimeLog.description)
                val totalCost = totalHours * selectedProject.cost.toDouble()

                etxtTrackedHours.setText(totalHours.toString())
                etxtCost.setText(totalCost.toString())
            }
            // method for when nothing is selected
            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(
                    requireContext(),
                    "Please select a project",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return root
    }
   // method to calculate total hours
    private fun calculateTotalHours(description: String): Double {
        // Assuming GlobalVar.GlobalVariables.oagTimeLog is a list of TimeLog objects
        return GlobalVar.GlobalVariables.oagTimeLog
            .filter { it.description == description }
            .sumOf { timeLog ->
                val duration = (timeLog.endTime?.minus(timeLog.startTime) ?: 0) / 3600000.0 // Convert milliseconds to hours
                duration
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


