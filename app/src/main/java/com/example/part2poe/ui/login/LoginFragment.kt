package com.example.part2poe.ui.login

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.part2poe.R
import com.example.part2poe.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnLogin.setOnClickListener {
            loginUser()
        }

        binding.iconViewPassword.setOnClickListener {
            togglePasswordVisibility()
        }

        binding.tvForgotPassword.setOnClickListener {
            onForgotPasswordClicked()
        }

        return root
    }

    override fun onResume() {
        super.onResume()

        // Clear the entered username and password fields when returning to the login screen
        binding.editUsername.text.clear()
        binding.editPassword.text.clear()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loginUser() {
        val username = binding.editUsername.text.toString()
        val password = binding.editPassword.text.toString()

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return

        val isPasswordUpdated = sharedPref.getBoolean("password_updated", false)

        if (isPasswordUpdated) {
            val newPassword = loginViewModel.getPassword(username)

            Log.d("LoginFragment", "Expected Username: $username, Expected Password: $newPassword")
            if (newPassword != null && password == newPassword) {
                Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        } else {
            val isValid = loginViewModel.isValidUser(username, password)
            if (isValid) {
                Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onForgotPasswordClicked() {
        val username = binding.editUsername.text.toString()
        val email = loginViewModel.getEmail(username) ?: ""

        // Navigate to the ForgetPasswordFragment
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToForgetpasswordFragment(username, email))
    }

private fun togglePasswordVisibility() {
        val inputType = binding.editPassword.inputType
        if (inputType == android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            binding.editPassword.inputType =
                android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.iconViewPassword.setImageResource(R.drawable.visibility_icon)
        } else {
            binding.editPassword.inputType = android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.iconViewPassword.setImageResource(R.drawable.visibility_icon)
        }
        if (binding.editPassword is android.widget.EditText) {
            binding.editPassword.setSelection(binding.editPassword.text.length)
        }
    }
}