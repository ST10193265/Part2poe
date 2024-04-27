package com.example.part2poe.ui.login

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.part2poe.R
import com.example.part2poe.databinding.FragmentLoginBinding
import com.example.part2poe.ui.home.HomeFragmentDirections

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var updatedPassword: String

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

        // Get the updated password from arguments if provided
        updatedPassword = requireArguments().getString("updatedPassword") ?: ""

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loginUser() {
        val username = binding.editUsername.text.toString()
        val password = binding.editPassword.text.toString()

        val isValid = loginViewModel.isValidUser(username, if (updatedPassword.isNotEmpty()) updatedPassword else password)
        if (isValid) {
            Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
            findNavController().navigate(HomeFragmentDirections.actionLoginFragmentToHomeFragment())
        } else {
            Toast.makeText(context, "Invalid username or password", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onForgotPasswordClicked() {
        val username = binding.editUsername.text.toString()
        val email = loginViewModel.getEmail(username) ?: ""

        val action = LoginFragmentDirections.actionLoginFragmentToForgetpasswordFragment(username, email)
        findNavController().navigate(action)
    }

    private fun togglePasswordVisibility() {
        val inputType = binding.editPassword.inputType
        if (inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            binding.editPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.iconViewPassword.setImageResource(R.drawable.visibility_icon)
        } else {
            binding.editPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.iconViewPassword.setImageResource(R.drawable.visibility_icon)
        }
        if (binding.editPassword is TextView) {
            binding.editPassword.setSelection(binding.editPassword.text.length)
        }
    }

}