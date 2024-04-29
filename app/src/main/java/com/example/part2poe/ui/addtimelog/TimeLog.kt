package com.example.part2poe.ui.addtimelog

import android.net.Uri

data class TimeLog(val description: String, val startTime: Long, var endTime: Long?, val calendar: String, val project: String, val imageUri: Uri?)
{

}
// stores all th project objects