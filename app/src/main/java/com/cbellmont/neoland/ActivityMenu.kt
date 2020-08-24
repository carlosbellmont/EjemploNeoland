package com.cbellmont.neoland

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

class ActivityMenu : AppCompatActivity() {

    companion object {
        fun getIntent(context: Context) : Intent {
            return Intent(context, ActivityMenu::class.java)
        }
    }
}