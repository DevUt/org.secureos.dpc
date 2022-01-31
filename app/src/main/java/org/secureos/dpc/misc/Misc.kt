package org.secureos.dpc.misc

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
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
        //TODO(Make a binding instead of this ugliness)
        val saveButton: Button = findViewById(R.id.misc_save)
        val minWipeTries: com.google.android.material.textfield.TextInputEditText =
            findViewById(R.id.wipe_retries)
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
        val vpnOnCheck : CheckBox = findViewById(R.id.vpn_check)
        val vpnLockdown : CheckBox = findViewById(R.id.vpn_lockdown_check)
        runBlocking {
            minWipeTries.setText(MiscPrefManager(this@Misc).readEnabled("wipe_retries")
                ?.toString()
                ?: "7")
            minPasswordLength.setText( MiscPrefManager(this@Misc).readEnabled("min_length")
                ?.toString()
                ?: "20")
            minCaps.setText(MiscPrefManager(this@Misc).readEnabled("min_uppercase")
                ?.toString()
                ?: "1")
            minLower.setText(MiscPrefManager(this@Misc).readEnabled("min_lowercase")
                ?.toString()
                ?: "1")
            minSpecial.setText(MiscPrefManager(this@Misc).readEnabled("min_special")
                ?.toString()
                ?: "1")
            minNum.setText(MiscPrefManager(this@Misc).readEnabled("min_number")
                ?.toString()
                ?: "1")

        }
        runBlocking {
            vpnOnCheck.isChecked = MiscPrefManager(this@Misc).readEnabled("vpn_always") == 2
            if(vpnOnCheck.isChecked){
                vpnLockdown.isClickable = true
                vpnLockdown.isChecked = MiscPrefManager(this@Misc).readEnabled("vpn_lockdown") == 1
            }else{
                vpnLockdown.isChecked = false
                vpnLockdown.isClickable = false
            }
        }
        vpnOnCheck.setOnClickListener {
            if(vpnOnCheck.isChecked){
                vpnLockdown.isClickable = true
            }else{
                vpnLockdown.isChecked = false
                vpnLockdown.isClickable = false
            }
        }
        saveButton.setOnClickListener {
            if (minWipeTries.text.toString().isBlank() ||
                minPasswordLength.text.toString().isBlank() ||
                minCaps.text.toString().isBlank() ||
                minLower.text.toString().isBlank() ||
                minSpecial.text.toString().isBlank() ||
                minNum.text.toString().isBlank()
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
                    if(vpnOnCheck.isChecked) {
                        miscPrefManager.writeEnabled(
                            "vpn_always",
                            2
                        )
                    }else{
                        miscPrefManager.writeEnabled(
                            "vpn_always",
                            1
                        )
                        miscPrefManager.writeEnabled(
                            "vpn_lockdown",
                            1
                        )
                    }
                    if(vpnLockdown.isClickable && vpnLockdown.isChecked){
                        miscPrefManager.writeEnabled(
                            "vpn_lockdown",
                            2
                        )
                    }
                }
            }
            Toast.makeText(this, "Saved Settings", Toast.LENGTH_SHORT).show()
        }

    }

}