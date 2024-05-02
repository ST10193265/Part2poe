package com.example.part2poe.ui.addtimelog

import android.net.Uri
import java.util.Date

data class TimeLog(val description: String, val startTime: Long, var endTime: Long?, val calendar: Date?, val project: String, val imageUri: Uri?)
{

}
// stores all the time log objects