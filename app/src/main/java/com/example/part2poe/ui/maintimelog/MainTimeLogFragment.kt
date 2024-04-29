package com.example.part2poe.ui.maintimelog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.part2poe.databinding.FragmentMaintimelogBinding

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
            navigateToAddCategory()
            // navigates to the add categroy
        }

        return root
    }

    private fun navigateToAddCategory() {
        findNavController().navigate(MainTimeLogFragmentDirections.actionMaintimelogFragmentToAddtimelogFragment())
    }
    // navigates to the ad category

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}