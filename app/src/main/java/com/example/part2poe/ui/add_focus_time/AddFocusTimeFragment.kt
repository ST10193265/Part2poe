package com.example.part2poe.ui.add_focus_time

import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.part2poe.databinding.FragmentAddFocusTimeBinding
import com.example.part2poe.ui.GlobalVar
import com.example.part2poe.ui.main_focus_time.MainFocusTimeFragmentDirections

class AddFocusTimeFragment : Fragment() {
    private var _binding: FragmentAddFocusTimeBinding? = null
    private val binding get() = _binding!!
    // bindes this page to the layout fragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddFocusTimeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val editProject = binding.spinProject
        val editTimePeriod = binding.etxtPeriodTime
        val editDate = binding.calDate
        val btnSave = binding.btnSave
        val btnCancel = binding.btnCancel
        // bindes the textview and buttons to this page so that code can be done for the buttons

        val projectNames = GlobalVar.GlobalVariables.oagProject.map { it.projectName }
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, projectNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        editProject.adapter = adapter
        // gets the projectname from the project array and bindes it to the spinner

        btnSave.setOnClickListener {
            val projectName = editProject.selectedItem.toString()
            val timePeriod = editTimePeriod.text.toString()
            val calendar = editDate.date.toString()
            // gets the values entered

            if (isValidInput(timePeriod, calendar)) {
                addNewFocusTime(projectName,timePeriod,calendar)
                // validates the dat in the textviews and then adds it to the focus class
            } else {
                Toast.makeText(requireContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show()
                // gives the user a msg if they have not entered values
            }

            navigateToMainFocusTime()
            // navigates to the main focus time

        }

        btnCancel.setOnClickListener {
            clearFields(editTimePeriod)
        }
        // clears the feilds

        return root
    }


    private fun addNewFocusTime(
        projectName: String,
        timePeriod: String,
        calendar: String
    ) {
        val focustime = FocusTime(projectName,timePeriod,calendar)
        GlobalVar.GlobalVariables.oagFocusTime.add(focustime)

        Toast.makeText(requireContext(), "New focus time added successfully!", Toast.LENGTH_SHORT).show()
    }
    // adds the focus time object to the focus time class
    private fun isValidInput(
        timePeriod: String,
        date: String
    ): Boolean {
        return  timePeriod.isNotEmpty() && date.isNotEmpty()
    }
    // validates the data

    private fun clearFields(vararg editTexts: EditText) {
        for (editText in editTexts) {
            editText.text.clear()
        }
    }
    // clears the feilds


    private fun navigateToMainFocusTime() {
        findNavController().navigate(MainFocusTimeFragmentDirections.actionMainFocusTimeFragmentToAddFocusTimeFragment())
    }
    // navigate to the main focus time

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

