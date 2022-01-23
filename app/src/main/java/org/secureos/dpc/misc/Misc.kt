package org.secureos.dpc.misc

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.runBlocking
import org.secureos.dpc.R
import java.security.AccessControlContext


class Misc : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_misc)
        val cameraSwitch = findViewById<com.google.android.material.switchmaterial.SwitchMaterial>(R.id.camera_switch)
        cameraSwitch.setOnClickListener {
            runBlocking {
                val writeCameraD = MiscPrefManager(this@Misc)
                writeCameraD.writeEnabled("camera",(cameraSwitch.isChecked==false).toInt())
            }
        }

    }
    fun Boolean.toInt() = if (this) 1 else 0

}