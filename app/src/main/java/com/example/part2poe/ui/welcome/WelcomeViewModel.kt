package com.example.part2poe.ui.welcome;

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
class WelcomeViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Welcome to Tick Tock. This app allows you to keep track of your times when completing your projects and tasks."
    }
    val text: LiveData<String> = _text
}