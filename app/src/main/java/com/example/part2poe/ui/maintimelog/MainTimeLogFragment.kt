package com.example.part2poe.ui.maintimelog

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.part2poe.databinding.FragmentMaintimelogBinding
import com.example.part2poe.ui.GlobalVar

class MainTimeLogFragment: Fragment() {
    private var _binding: FragmentMaintimelogBinding? = null
    private val binding get() = _binding!!
    // bindes the layout fragement

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMaintimelogBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Find the Add New Category button and set a click listener
        val addTimeLogButton: Button = binding.btnAddTimeLog
        addTimeLogButton.setOnClickListener {
            navigateToAddTimeLog()
            // navigates to the add categroy
        }

        val searchView: androidx.appcompat.widget.SearchView = binding.searchView
        val cbDate: CheckBox = binding.cbDate
        val listTimeLogs: ListView = binding.listTimeLogs

        // Initialize the list adapter for the ListView
        val timeLogDescriptions = GlobalVar.GlobalVariables.oagTimeLog.map { it.description }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, timeLogDescriptions)
        listTimeLogs.adapter = adapter

        // Set up the SearchView to filter the list based on the project name and date
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                query?.let { filterTimeLogs(it) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // As the user types, filter the list
                newText?.let { filterTimeLogs(it) }
                return false
            }
        })

        // Find the Add New Category button and set a click listener
        val endTimeLogButton: Button = binding.btnEndTimeLog
        val editTimeLog = binding.dpTimeLog

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        editTimeLog.adapter = adapter

        val timeLogDescription = GlobalVar.GlobalVariables.oagTimeLog.map { it.description }


        endTimeLogButton.setOnClickListener {
            val selectedPosition = binding.dpTimeLog.selectedItemPosition

            val adapter =
                ArrayAdapter(requireContext(), R.layout.simple_spinner_item, timeLogDescription)


            if (selectedPosition >= 0) {

                val selectedTimeLog = GlobalVar.GlobalVariables.oagTimeLog[selectedPosition]

                selectedTimeLog.endTime = System.currentTimeMillis()

                adapter.notifyDataSetChanged()

                Toast.makeText(requireContext(), "Time log ended", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please select a time log to end",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return root
    }

    private fun navigateToAddTimeLog() {
        findNavController().navigate(MainTimeLogFragmentDirections.actionMaintimelogFragmentToAddtimelogFragment())
    }
    // navigates to the ad category

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun filterTimeLogs(query: String) {
        // Filter the time logs based on the project name
        val filteredList = GlobalVar.GlobalVariables.oagTimeLog.filter { timeLog ->
            timeLog.description.contains(query, ignoreCase = true)
        }.map { it.description }

        // Update the ListView adapter with the filtered list
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, filteredList)
        binding.listTimeLogs.adapter = adapter
    }

}