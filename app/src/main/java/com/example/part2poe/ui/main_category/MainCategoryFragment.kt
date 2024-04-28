package com.example.part2poe.ui.main_category

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.part2poe.R
import com.example.part2poe.databinding.FragmentMainCategoryBinding

class MainCategoryFragment : Fragment() {
    private var _binding: FragmentMainCategoryBinding? = null
    private val binding get() = _binding!!
    // bindes the layout fragement

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainCategoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Find the Add New Category button and set a click listener
        val addCategoryButton: Button = binding.btnAddNewCategory
        addCategoryButton.setOnClickListener {
            navigateToAddCategory()
            // navigates to the add categroy
        }

        return root
    }

    private fun navigateToAddCategory() {
        findNavController().navigate(MainCategoryFragmentDirections.actionMainCategoryFragmentToAddCategoryFragment())
    }
    // navigates to the ad category

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}