package com.example.part2poe.ui

import com.example.part2poe.ui.add_category.Category
import com.example.part2poe.ui.add_focus_time.FocusTime
import com.example.part2poe.ui.add_project.Project
import com.example.part2poe.ui.addtimelog.TimeLog

//import com.example.part2poe.ui.add_focus_time.FocusTime

class GlobalVar {
    object GlobalVariables {

        val oagTimeLog= ArrayList<TimeLog>()

        val oagCategory = ArrayList<Category>()

        val oagProject = ArrayList<Project>()

        val oagFocusTime = ArrayList<FocusTime>()

    }
}

