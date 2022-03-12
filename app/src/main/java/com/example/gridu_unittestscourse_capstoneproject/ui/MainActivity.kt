package com.example.gridu_unittestscourse_capstoneproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import com.example.gridu_unittestscourse_capstoneproject.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun setProgressBar(visibility: Boolean) {
        findViewById<ProgressBar>(R.id.progressBar).visibility =
            if (visibility) View.VISIBLE else View.GONE
    }
}