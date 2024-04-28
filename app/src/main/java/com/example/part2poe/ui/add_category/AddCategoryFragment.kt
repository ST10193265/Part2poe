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

        btnSave.setOnClickListener()
        {
            val categoryname = editCategoryName.text.toString().trim()
            val description = editDescription.text.toString().trim()

            if (isValidInput(categoryname, description)) {
                addnewCategory(categoryname, description)

                val arrayAsString = GlobalVar.GlobalVariables.oagCategory.joinToString("\n")



                val stringBuilder = StringBuilder()

                GlobalVar.GlobalVariables.oagCategory.forEach { item ->
                    stringBuilder.append(item.categoryname).append("\n")
                }


            }
            else {
                Toast.makeText(
                    requireContext(),
                    "Please fill in all the fields",
                    Toast.LENGTH_SHORT
                ).show()
            }



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


    private fun addnewCategory(categoryname: String, description: String)
    {
        val category = Category(categoryname, description)

        GlobalVar.GlobalVariables.oagCategory.add(category)
        Toast.makeText(requireContext(), "New category added successful!", Toast.LENGTH_SHORT)
            .show()


        // Call the clearFields and navigateToMainCategory functions here
        clearFields(binding.etxtCategoryName, binding.etxtDescription)
        navigateToMainCatgeory()
    }


    private fun clearFields(vararg editTexts: EditText) {
        for (editText in editTexts) {
            editText.text.clear()
        }
    }

    private fun navigateToMainCatgeory() {
        findNavController().navigate(MainCategoryFragmentDirections.actionAddCategoryFragmentToMainCategoryFragment())
    }

}