import android.R
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

import android.widget.Button
import androidx.navigation.fragment.findNavController

import com.example.part2poe.databinding.FragmentMainCategoryBinding
import com.example.part2poe.ui.GlobalVar
import com.example.part2poe.ui.main_category.MainCategoryFragmentDirections

class MainCategoryFragment : Fragment() {
    private var _binding: FragmentMainCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainCategoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val testlist = binding.testlist

        // Create a list of category names
        val categoryNames = GlobalVar.GlobalVariables.oagCategory.map { it.categoryname }

        // Create an adapter using the category names and a layout for list items
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, categoryNames)

       // testlist.adapter = adapter

        // Find the Add New Category button and set a click listener
        val addCategoryButton: Button = binding.btnAddNewCategory
        addCategoryButton.setOnClickListener {
            navigateToAddCategory()
        }

        return root
    }

    private fun navigateToAddCategory() {
        findNavController().navigate(MainCategoryFragmentDirections.actionMainCategoryFragmentToAddCategoryFragment())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
