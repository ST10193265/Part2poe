package com.example.part2poe.ui.add_category

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.part2poe.R
import com.example.part2poe.databinding.FragmentAddCategoryBinding
import com.example.part2poe.ui.GlobalVar
import com.example.part2poe.ui.main_category.MainCategoryFragmentDirections

class AddCategoryFragment : Fragment() {

    private var _binding: FragmentAddCategoryBinding? = null
    private val binding get() = _binding!!
    // binds the layout fragment

    // Lazily initialize loginViewModel
    private val addCategoryViewModel: AddCategoryViewModel by lazy {
        ViewModelProvider(requireActivity()).get(AddCategoryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddCategoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val editCategoryName = binding.etxtCategoryName
        val editDescription = binding.etxtDescription
        val btnSave = binding.btnSave
        val btnCancel = binding.btnCancel
        // binds all the feilds

        btnSave.setOnClickListener()
        {
            val categoryname = editCategoryName.text.toString().trim()
            val description = editDescription.text.toString().trim()

            if (isValidInput(categoryname, description)) {
                addnewCategory(categoryname, description)
                // validates the data entered then adds it to the category class
                navigateToMainCatgeory()
            }
            else {
                Toast.makeText(
                    requireContext(),
                    "Please fill in all the fields",
                    Toast.LENGTH_SHORT
                ).show()
                // gives the user a msg if no data is entered
            }
            //navigateToMainCatgeory()
           // findNavController().navigate(MainCategoryFragmentDirections.actionAddCategoryFragmentToMainCategoryFragment())


        }
        btnCancel.setOnClickListener()
        {
            // Call the clearFields and navigateToMainCategory functions here
            clearFields(binding.etxtCategoryName, binding.etxtDescription)

        }
        return root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun isValidInput(
        categoryname: String,
        description: String
    ): Boolean {
        return categoryname.isNotEmpty() && description.isNotEmpty()
    }
    // validates the data

    private fun addnewCategory(categoryname: String, description: String)
    {
        val category = Category(categoryname, description)
        // saves to the ategory class
        GlobalVar.GlobalVariables.oagCategory.add(category)
        Toast.makeText(requireContext(), "New category added successful!", Toast.LENGTH_SHORT)
            .show()
        // gives the user a msg that the category is added

        // Call the clearFields and navigateToMainCategory functions here
        clearFields(binding.etxtCategoryName, binding.etxtDescription)

    }


    private fun clearFields(vararg editTexts: EditText) {
        for (editText in editTexts) {
            editText.text.clear()
        }
    }
    // clears the feilds

    private fun navigateToMainCatgeory() {
        findNavController().navigate(MainCategoryFragmentDirections.actionAddCategoryFragmentToMainCategoryFragment())
    }
    // navigates to the main category

}