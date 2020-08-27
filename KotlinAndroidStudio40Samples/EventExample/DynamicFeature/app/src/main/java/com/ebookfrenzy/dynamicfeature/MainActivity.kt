package com.ebookfrenzy.dynamicfeature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent
import android.widget.Toast
import android.app.Activity
import android.content.IntentSender

import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.play.core.splitinstall.SplitInstallRequest

import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import java.util.*

class MainActivity : AppCompatActivity() {

    private val REQUESTCODE = 101
    private lateinit var manager: SplitInstallManager
    private var mySessionId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = SplitInstallManagerFactory.create(this)
    }

    fun launchFeature(view: View) {

        if (manager.installedModules.contains("my_dynamic_feature")) {
            startActivity(Intent(
                    "com.ebookfrenzy.my_dynamic_feature.MyFeatureActivity"))
        } else {
            status_text.text = "Feature not yet installed"
        }
    }

    fun installFeature(view: View) {

        manager.registerListener(listener)

        val request = SplitInstallRequest.newBuilder()
                .addModule("my_dynamic_feature")
                .build()

        manager.startInstall(request)
                .addOnSuccessListener { sessionId ->
                    mySessionId = sessionId
                    Toast.makeText(this@MainActivity,
                            "Module installation started",
                            Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this@MainActivity,
                            "Module installation failed: $exception",
                            Toast.LENGTH_SHORT).show()
                }
    }

    private var listener: SplitInstallStateUpdatedListener =
            SplitInstallStateUpdatedListener { state ->
                if (state.sessionId() == mySessionId) {
                    when (state.status()) {

                        SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {

                            status_text.text =
                                    "Large Feature Module. Requesting Confirmation"

                            try {
                                manager.startConfirmationDialogForResult(
                                        state,
                                        this@MainActivity, REQUESTCODE
                                )
                            } catch (ex: IntentSender.SendIntentException) {
                                status_text.text = "Confirmation Request Failed."
                            }
                        }

                        SplitInstallSessionStatus.DOWNLOADING -> {
                            val size = state.totalBytesToDownload()
                            val downloaded = state.bytesDownloaded()
                            status_text.text =
                                    String.format(Locale.getDefault(),
                                            "%d of %d bytes downloaded.", downloaded, size)
                        }

                        SplitInstallSessionStatus.INSTALLING ->
                            status_text.text = "Installing feature"

                        SplitInstallSessionStatus.DOWNLOADED ->
                            status_text.text = "Download Complete"

                        SplitInstallSessionStatus.INSTALLED ->
                            status_text.text = "Installed - Feature is ready"

                        SplitInstallSessionStatus.CANCELED ->
                            status_text.text = "Installation cancelled"

                        SplitInstallSessionStatus.PENDING ->
                            status_text.text = "Installation pending"
                        SplitInstallSessionStatus.FAILED ->

                            status_text.text =
                                    String.format(Locale.getDefault(),
                                            "Installation Failed. Error code: %s", state.errorCode())
                    }
                }
            }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUESTCODE) {
            if (resultCode == Activity.RESULT_OK) {
                status_text.text = "Beginning Installation."
            } else {
                status_text.text = "User declined installation."
            }
        }
    }

    override fun onResume() {
        manager.registerListener(listener)
        super.onResume()
    }

    override fun onPause() {
        manager.unregisterListener(listener)
        super.onPause()
    }

    fun deleteFeature(view: View) {
        manager.deferredUninstall(Arrays.asList("my_dynamic_feature"))
    }

}
