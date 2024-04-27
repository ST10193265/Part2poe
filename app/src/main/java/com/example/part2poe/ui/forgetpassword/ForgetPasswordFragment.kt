package com.example.part2poe.ui.forgetpassword

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.part2poe.R
import com.example.part2poe.databinding.FragmentLoginBinding
import com.example.part2poe.ui.login.LoginViewModel

class ForgetPasswordFragment : Fragment() {

    private lateinit var forgetPasswordViewModel: ForgetPasswordViewModel
    private lateinit var username: String
    private lateinit var email: String
    private lateinit var newPassword: String
    private var _binding: FragmentLoginBinding? = null
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



        forgetPasswordViewModel = ViewModelProvider(this).get(ForgetPasswordViewModel::class.java)

        // Retrieve username and email from arguments
        username = arguments?.getString("username") ?: ""
        email = arguments?.getString("email") ?: ""

        val initialPassword = forgetPasswordViewModel.getPassword(username) ?: ""
        forgetPasswordViewModel.setRegisteredUserCredentials(username, email, initialPassword)

        Log.d("ForgetPasswordFragment", "Initialized with username: $username, email: $email, password: $initialPassword")

        val root = inflater.inflate(R.layout.fragment_forgetpassword, container, false)
        val editEmail: EditText = root.findViewById(R.id.edit_email)
        val editUsername: EditText = root.findViewById(R.id.edit_username)
        val editPassword: EditText = root.findViewById(R.id.edit_password)
        val btnSetPassword: Button = root.findViewById(R.id.btn_newpassword)

        editEmail.setText(email)
        editUsername.setText(username)

        btnSetPassword.setOnClickListener {
            newPassword = editPassword.text.toString().trim()
            if (forgetPasswordViewModel.isValidUser(username, email)) {

                Log.d("ForgetPasswordFragment", "Current Password for $username: ${forgetPasswordViewModel.getPassword(username)}")

                val isPasswordUpdated = forgetPasswordViewModel.updatePassword(username, newPassword)
                loginViewModel.setUpdatedPassword(newPassword)
                Log.d("ForgetPasswordFragment", "Password update result: $isPasswordUpdated")

                if (isPasswordUpdated) {
                    // Save the flag indicating password is updated
                    val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return@setOnClickListener
                    with(sharedPref.edit()) {
                        putBoolean("password_updated", true)
                        apply()
                    }

                    Log.d("ForgetPasswordFragment", "Updated Password for $username: $newPassword")

                    Log.d("ForgetPasswordFragment", "Expected Username: $username, Expected Password: $newPassword")
                    Toast.makeText(context, "Password updated successfully", Toast.LENGTH_SHORT).show()
                    navigateToLogin(newPassword)
                } else {
                    Toast.makeText(context, "Failed to update password", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Invalid username or email", Toast.LENGTH_SHORT).show()
            }
        }
        return root
    }
    private fun navigateToLogin(updatedPassword: String) {
        val action = ForgetPasswordFragmentDirections.actionForgetpasswordFragmentToLoginFragment(newPassword)
        findNavController().navigate(action)
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
