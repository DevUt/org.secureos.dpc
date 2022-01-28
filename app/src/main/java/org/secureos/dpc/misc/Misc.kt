package org.secureos.dpc.misc

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.runBlocking
import org.secureos.dpc.R


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
        val minPasswordLength: com.google.android.material.textfield.TextInputEditText =
            findViewById(R.id.min_password)
        val minCaps: com.google.android.material.textfield.TextInputEditText =
            findViewById(R.id.min_caps)
        val minLower: com.google.android.material.textfield.TextInputEditText =
            findViewById(R.id.min_lower)
        val minSpecial: com.google.android.material.textfield.TextInputEditText =
            findViewById(R.id.spl_char)
        val minNum: com.google.android.material.textfield.TextInputEditText =
            findViewById(R.id.min_num)
        runBlocking {
            findViewById<TextView>(R.id.password_wipe_length).text = MiscPrefManager(this@Misc).readEnabled("wipe_retries")
                ?.toString()
                ?: "7"
            findViewById<TextView>(R.id.min_password).text = MiscPrefManager(this@Misc).readEnabled("min_length")
                ?.toString()
                ?: "20"
            findViewById<TextView>(R.id.min_caps).text = MiscPrefManager(this@Misc).readEnabled("min_uppercase")
                ?.toString()
                ?: "1"
            findViewById<TextView>(R.id.min_lower).text = MiscPrefManager(this@Misc).readEnabled("min_lowercase")
                ?.toString()
                ?: "1"
            findViewById<TextView>(R.id.spl_char).text = MiscPrefManager(this@Misc).readEnabled("min_special")
                ?.toString()
                ?: "1"
            findViewById<TextView>(R.id.min_num).text = MiscPrefManager(this@Misc).readEnabled("min_number")
                ?.toString()
                ?: "1"
        }
        saveButton.setOnClickListener {
            if (minWipeTries.text.toString().isNullOrBlank() ||
                minPasswordLength.text.toString().isNullOrBlank() ||
                minCaps.text.toString().isNullOrBlank() ||
                minLower.text.toString().isNullOrBlank() ||
                minSpecial.text.toString().isNullOrBlank() ||
                minNum.text.toString().isNullOrBlank()
            ) {
                Toast.makeText(this, "Text Field Can't be empty", Toast.LENGTH_LONG).show()
            } else {
                runBlocking {
                    val miscPrefManager = MiscPrefManager(this@Misc)
                    miscPrefManager.writeEnabled(
                        "wipe_retries",
                        minWipeTries.text.toString().toInt()
                    )
                    miscPrefManager.writeEnabled(
                        "min_length",
                        minPasswordLength.text.toString().toInt()
                    )
                    miscPrefManager.writeEnabled(
                        "min_uppercase",
                        minCaps.text.toString().toInt()
                    )
                    miscPrefManager.writeEnabled(
                        "min_lowercase",
                        minLower.text.toString().toInt()
                    )
                    miscPrefManager.writeEnabled(
                        "min_special",
                        minSpecial.text.toString().toInt()
                    )
                    miscPrefManager.writeEnabled(
                        "min_number",
                        minNum.text.toString().toInt()
                    )
                }
            }
            Toast.makeText(this, "Saved Settings", Toast.LENGTH_SHORT).show()
        }

    }

    private fun Boolean.toInt() = if (this) 1 else 0

}