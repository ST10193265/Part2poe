package com.example.part2poe.ui.register

import LoginViewModel
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.part2poe.R
import com.example.part2poe.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    // Lazily initialize loginViewModel
    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val editName = binding.editName
        val editSurname = binding.editSurname
        val editEmail = binding.editEmail
        val editUsername = binding.editUsername
        val editPassword = binding.editPassword
        val btnRegister = binding.btnRegister
        val iconViewPassword = binding.iconViewPassword

        btnRegister.setOnClickListener {
            val name = editName.text.toString().trim()
            val surname = editSurname.text.toString().trim()
            val email = editEmail.text.toString().trim()
            val username = editUsername.text.toString().trim()
            val password = editPassword.text.toString().trim()

            if (isValidInput(name, surname, email, username, password)) {
                registerUser(name, surname, email, username, password)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please fill in all the fields",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        iconViewPassword.setOnClickListener {
            togglePasswordVisibility(editPassword, iconViewPassword)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun isValidInput(
        name: String,
        surname: String,
        email: String,
        username: String,
        password: String
    ): Boolean {
        return name.isNotEmpty() && surname.isNotEmpty() && email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()
    }

    private fun registerUser(
        name: String,
        surname: String,
        email: String,
        username: String,
        password: String
    ) {
        Toast.makeText(requireContext(), "Registration successful!", Toast.LENGTH_SHORT).show()

        // Update the LoginViewModel with the registered credentials
        loginViewModel.setRegisteredUserCredentials(username, password)

        // Call the clearFields and navigateToLogin functions here
        clearFields(binding.editName, binding.editSurname, binding.editEmail, binding.editUsername, binding.editPassword)
        navigateToLogin()
    }

    private fun clearFields(vararg editTexts: EditText) {
        for (editText in editTexts) {
            editText.text.clear()
        }
    }

    private fun navigateToLogin() {
        findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
    }

    private fun togglePasswordVisibility(editPassword: TextView, iconViewPassword: View) {
        val inputType = editPassword.inputType
        if (inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            editPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            iconViewPassword.setBackgroundResource(R.drawable.visibility_icon)
        } else {
            editPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            iconViewPassword.setBackgroundResource(R.drawable.visibility_icon)
        }
        if (editPassword is EditText) {
            editPassword.setSelection(editPassword.text.length)
        }
    }
}