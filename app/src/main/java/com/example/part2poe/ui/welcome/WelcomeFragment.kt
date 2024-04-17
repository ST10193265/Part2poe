package com.example.part2poe.ui.welcome;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.part2poe.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val welcomeViewModel = ViewModelProvider(this).get(WelcomeViewModel::class.java)

        val textView = binding.textWelcome
        welcomeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        // Find the Register button and set a click listener
        val registerButton: Button = binding.registerButton
        registerButton.setOnClickListener {
            navigateToRegister()
        }

        // Find the Login button and set a click listener
        val loginButton: Button = binding.loginButton
        loginButton.setOnClickListener {
            navigateToLogin()
        }

        return root
    }

    private fun navigateToRegister() {
        findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToRegisterFragment())
    }

    private fun navigateToLogin() {
        findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToLoginFragment())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

