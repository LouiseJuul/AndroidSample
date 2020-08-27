package com.ebookfrenzy.asyncdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.os.AsyncTask

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private inner class MyTask : AsyncTask<String, Int, String>() {

        override fun onPreExecute() {

        }

        override fun doInBackground(vararg params: String): String {
            var i = 0
            while (i <= 20) {
                try {
                    Thread.sleep(1000)
                    publishProgress(i)
                    i++
                }
                catch (e: Exception) {
                    return(e.localizedMessage)
                }
            }
            return "Button Pressed"
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            val counter = values.get(0)
            myTextView.text = "Counter = $counter"
        }

        override fun onPostExecute(result: String) {
            myTextView.text = result
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun buttonClick(view: View) {
        val task = MyTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)

    }

}