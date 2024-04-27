package com.example.part2poe.ui.maintimelog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.part2poe.R
import com.example.part2poe.ui.register.RegisterFragmentDirections

class MainTimeLogFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_maintimelog, container, false)

        val btnAddTimeLog: Button = view.findViewById(R.id.btnAddTimeLog)
        btnAddTimeLog.setOnClickListener {

            findNavController().navigate(MainTimeLogFragmentDirections.actionMaintimelogFragmentToAddtimelogFragment())
        }

        return view
    }

}