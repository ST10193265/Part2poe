package com.example.part2poe.ui.maintimelog

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
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

        // Find the Add New Category button and set a click listener
        val endTimeLogButton: Button = binding.btnEndTimeLog
        endTimeLogButton.setOnClickListener {
            val selectedPosition = binding.dpTimeLog.selectedItemPosition

            val editTimeLog = binding.dpTimeLog

            val timeLogDescription = GlobalVar.GlobalVariables.oagTimeLog.map { it.description }

            val adapter =
                ArrayAdapter(requireContext(), R.layout.simple_spinner_item, timeLogDescription)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            editTimeLog.adapter = adapter

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

}