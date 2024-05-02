package com.example.part2poe.ui.main_focus_time

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.part2poe.R

import com.example.part2poe.databinding.FragmentMainFocusTimeBinding
import com.example.part2poe.ui.add_focus_time.AddFocusTimeViewModel
import com.example.part2poe.ui.main_category.MainCategoryFragmentDirections


class MainFocusTimeFragment : Fragment() {
    private var _binding: FragmentMainFocusTimeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainFocusTimeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val addFocusTimeViewModel = ViewModelProvider(this).get(AddFocusTimeViewModel::class.java)

        val homeButton: Button = binding.btnHome
        homeButton.setOnClickListener {
            findNavController().navigate(MainFocusTimeFragmentDirections.actionMainFocusTimeFragmentToHomeFragment())
        }

        val addFocusTimeButton: Button = binding.btnAddFocusTime
        addFocusTimeButton.setOnClickListener {
            navigateToAddFocusTime()
            //Toast.makeText(requireContext(), GlobalVar.GlobalVariables.oagProject.size, Toast.LENGTH_SHORT)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToAddFocusTime(){
        findNavController().navigate(MainFocusTimeFragmentDirections.actionMainFocusTimeFragmentToAddFocusTimeFragment())
    }

}