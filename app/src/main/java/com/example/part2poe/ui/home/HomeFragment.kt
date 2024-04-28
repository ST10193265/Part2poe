package com.example.part2poe.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.part2poe.R


class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var buttonCategory: Button
    private lateinit var buttonProject: Button
    private lateinit var buttonTimeLog: Button
    private lateinit var buttonInvoice: Button
    private lateinit var buttonFocusTime: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize buttons and set click listeners
        buttonCategory = view.findViewById(R.id.button_category)
        buttonProject = view.findViewById(R.id.button_project)
        buttonTimeLog = view.findViewById(R.id.button_timelog)
        buttonInvoice = view.findViewById(R.id.button_invoice)
        buttonFocusTime = view.findViewById(R.id.button_focustime)

        buttonCategory.setOnClickListener(this)
        buttonProject.setOnClickListener(this)
        buttonTimeLog.setOnClickListener(this)
        buttonInvoice.setOnClickListener(this)
        buttonFocusTime.setOnClickListener(this)

        return view
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_category -> {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMainCategoryFragment())
            }

            R.id.button_project -> {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMainProjectFragment())
            }

            R.id.button_timelog -> {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMaintimelogFragment())
            }

            R.id.button_invoice -> {
                // Handle Invoice button click
            }

            R.id.button_focustime -> {
               findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMainFocusTimeFragment())

            }
        }
    }
}