package org.secureos.dpc.misc

import android.os.Bundle
import android.widget.Button
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
        saveButton.setOnClickListener {
            if (minWipeTries.text.toString().isNullOrBlank() ||
                minPasswordLength.text.toString().isNullOrBlank() ||
                minCaps.text.toString().isNullOrBlank() ||
                minLower.text.toString().isNullOrBlank() ||
                minSpecial.text.toString().isNullOrBlank() ||
                minNum.text.toString().isNullOrBlank()
            ) {
                Toast.makeText(this, "Text Field Can't be empty", Toast.LENGTH_LONG).show()
                recreate()
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