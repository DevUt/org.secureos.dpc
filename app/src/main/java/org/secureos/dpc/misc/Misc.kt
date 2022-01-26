package org.secureos.dpc.misc

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import kotlinx.coroutines.runBlocking
import org.secureos.dpc.MainActivity
import org.secureos.dpc.R
import java.security.AccessControlContext


class Misc : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_misc)
//        val cameraSwitch =
//            findViewById<com.google.android.material.switchmaterial.SwitchMaterial>(R.id.camera_switch)
//        var currCameraStatus: Int?
//        runBlocking {
//            currCameraStatus = MiscPrefManager(this@Misc).readEnabled("camera")
//        }
//        if (currCameraStatus != null) {
//            cameraSwitch.isChecked = (currCameraStatus == 1)
//        }
//        cameraSwitch.setOnClickListener {
//            runBlocking {
//                val writeCameraD = MiscPrefManager(this@Misc)
//                writeCameraD.writeEnabled("camera", (cameraSwitch.isChecked).toInt())
//            }
//        }

        val saveButton: Button = findViewById(R.id.misc_save)
        val minWipeTries: com.google.android.material.textfield.TextInputEditText =
            findViewById(R.id.password_wipe_length)
        saveButton.setOnClickListener {
            if (minWipeTries.text.toString().isNullOrBlank()) {
                Toast.makeText(this, "Text Field Can't be empty", Toast.LENGTH_LONG).show()
                recreate()
            } else {
                runBlocking {
                    MiscPrefManager(this@Misc).writeEnabled(
                        "wipe_retries",
                        minWipeTries.text.toString().toInt()
                    )
                }
            }
            Toast.makeText(this,"Saved Settings",Toast.LENGTH_SHORT).show()
        }
        runBlocking {
            val wipeRetriesD = MiscPrefManager(this@Misc).readEnabled("wipe_retries")
            if (wipeRetriesD == 0) {
                minWipeTries.setText("5")
            } else {
                minWipeTries.setText(wipeRetriesD.toString())
            }
        }

    }

    private fun Boolean.toInt() = if (this) 1 else 0

}