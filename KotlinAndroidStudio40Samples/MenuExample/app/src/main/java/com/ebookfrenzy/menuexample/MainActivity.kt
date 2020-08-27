package com.ebookfrenzy.menuexample

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.menu_red -> {
                item.isChecked = !item.isChecked
                layoutView.setBackgroundColor(android.graphics.Color.RED)
                return true
            }
            R.id.menu_green -> {
                item.isChecked = !item.isChecked
                layoutView.setBackgroundColor(android.graphics.Color.GREEN)
                return true
            }
            R.id.menu_yellow -> {
                item.isChecked = !item.isChecked
                layoutView.setBackgroundColor(android.graphics.Color.YELLOW)
                return true
            }
            R.id.menu_blue -> {
                item.isChecked = !item.isChecked
                layoutView.setBackgroundColor(android.graphics.Color.BLUE)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }
}