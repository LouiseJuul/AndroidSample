package com.ebookfrenzy.eventexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myButton.setOnClickListener {
            statusText.text = "Button clicked"
        }

        myButton.setOnLongClickListener {
            statusText.text = "Long button click"
            false
        }


    }
}