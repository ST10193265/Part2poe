package com.example.part2poe.ui.main_focus_time

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.part2poe.R

class MainFocusTimeFragment : Fragment() {

    companion object {
        fun newInstance() = MainFocusTimeFragment()
    }

    private val viewModel: MainFocusTimeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main_focus_time, container, false)
    }
}