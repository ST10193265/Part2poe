package com.example.part2poe.ui.register


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
import com.example.part2poe.ui.login.LoginViewModel

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

        // this method was adapted from android developer
        // https://developer.android.com/topic/libraries/view-binding

        // listener for register button
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

                // this method was adapted in geeksforgeeks
                // https://www.geeksforgeeks.org/android-toast-in-kotlin/
                // SriHarshaBammidi
                // https://auth.geeksforgeeks.org/user/SriHarshaBammidi/articles?utm_source=geeksforgeeks&utm_medium=article_author&utm_campaign=auth_user

            }
        }
        // listener for icon view password
        iconViewPassword.setOnClickListener {
            togglePasswordVisibility(editPassword, iconViewPassword)
        }

        // the method above were adapted android
        // https://kotlinandroid.org/button/setonclicklistener/#:~:text=Steps%20to%20call%20setOnClickListener%20%28%29%20on%20Button%201,executed%20when%20a%20tap%20happens%20on%20the%20button.

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // checking valid inputs
    private fun isValidInput(
        name: String,
        surname: String,
        email: String,
        username: String,
        password: String
    ): Boolean {
        return name.isNotEmpty() && surname.isNotEmpty() && email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()
    }

    // register user method
    private fun registerUser(
        name: String,
        surname: String,
        email: String,
        username: String,
        password: String
    ) {
        Toast.makeText(requireContext(), "Registration successful!", Toast.LENGTH_SHORT).show()

        // Update the LoginViewModel with the registered credentials
        loginViewModel.setRegisteredUserCredentials(username, password, email)

        // Call the clearFields and navigateToLogin functions here
        clearFields(binding.editName, binding.editSurname, binding.editEmail, binding.editUsername, binding.editPassword)
        navigateToLogin()
    }

    private fun clearFields(vararg editTexts: EditText) {
        for (editText in editTexts) {
            editText.text.clear()
        }
        // clears feilds
        // this method was adapted from techiedelight
        // https://www.techiedelight.com/clear-list-in-kotlin/#:~:text=Clear%20a%20List%20in%20Kotlin%201%201.%20Using,remove%28%29%20function%20...%204%204.%20Using%20Iterator.remove%28%29%20function
        // vivek-srivastava
        // https://www.techiedelight.com/vivek-srivastava/
    }

    private fun navigateToLogin() {
        findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
    }

    //navigates to main time log
    // this method has been adapted from android developer
    // https://developer.android.com/guide/navigation/navcontroller

    // method to making password visible
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
    // this method was adapted from stack overflow
    // https://stackoverflow.com/questions/3685790/how-to-switch-between-hide-and-view-password
    // mmbs
    // https://stackoverflow.com/users/2065587/mmbs

}