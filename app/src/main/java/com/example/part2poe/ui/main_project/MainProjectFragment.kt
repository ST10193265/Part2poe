package com.example.part2poe.ui.main_project

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
import com.example.part2poe.databinding.FragmentMainCategoryBinding
import com.example.part2poe.databinding.FragmentMainProjectBinding
import com.example.part2poe.ui.main_category.MainCategoryFragmentDirections
import com.example.part2poe.ui.main_category.MainCategoryViewModel

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

        return root
    }

    private fun navigateToAddProject() {
        findNavController().navigate(MainProjectFragmentDirections.actionMainProjectFragmentToAddProjectFragment())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}