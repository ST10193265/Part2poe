package com.example.part2poe.ui.invoice

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.part2poe.databinding.FragmentInvoiceBinding
import com.example.part2poe.ui.GlobalVar

class InvoiceFragment: Fragment() {
    private var _binding: FragmentInvoiceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInvoiceBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val dpProject = binding.dpProject
        val etxtTrackedHours = binding.etxtTrackedHours
        val etxtCost = binding.etxtCost

        // Assuming GlobalVar.GlobalVariables.oagProject is a list of Project objects
        val projectNames = GlobalVar.GlobalVariables.oagProject.map { it.projectName }
        val projectAdapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, projectNames)
        projectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dpProject.adapter = projectAdapter

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


