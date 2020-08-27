package com.ebookfrenzy.coroutinedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.view.View

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private var count: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seekBar?.setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                count = progress
                countText.text = "${count} coroutines"
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
            }
        })

    }

    fun launchCoroutines(view: View) {

        (1..count).forEach {
            statusText.text = "Started Coroutine ${it}"
            coroutineScope.launch(Dispatchers.Main) {
                statusText.text = performTask(it).await()
            }
        }
    }

    suspend fun performTask(tasknumber: Int): Deferred<String> =
            coroutineScope.async(Dispatchers.Main) {
                delay(5_000)
                return@async "Finished Coroutine ${tasknumber}"
            }


}