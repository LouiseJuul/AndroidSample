package com.ebookfrenzy.webprint

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebResourceRequest
import android.content.Context
import kotlinx.android.synthetic.main.fragment_first.*
import android.print.PrintAttributes
import android.print.PrintManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

    }

    override fun onStart() {
        super.onStart()
        configureWebView()
    }

    private fun configureWebView() {

        myWebView?.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                    view: WebView, request: WebResourceRequest): Boolean {
                return super.shouldOverrideUrlLoading(
                        view, request)
            }
        }
        myWebView?.loadUrl(
                "https://developer.android.com/google/index.html")
    }

    private fun createWebPrintJob(webView: WebView?) {

        val printManager = this
                .getSystemService(Context.PRINT_SERVICE) as PrintManager

        val printAdapter = webView?.createPrintDocumentAdapter("MyDocument")

        val jobName = getString(R.string.app_name) + " Print Test"

        printAdapter?.let {
            printManager.print(
                    jobName, it,
                    PrintAttributes.Builder().build()
            )
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
        if (item.itemId == R.id.action_print) {
            createWebPrintJob(myWebView)
        }
        return super.onOptionsItemSelected(item)

    }
}