package org.sos.asp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.runBlocking
import org.sos.asp.deviceAdmin.DeviceAdmin
import org.sos.asp.misc.MiscPrefManager

class MainEnforced : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_enforcer)
        val deviceObj = DeviceAdmin()
        if (!deviceObj.isAdmin(this)) {
            runBlocking {
                MiscPrefManager(this@MainEnforced).writeEnabled("enforced", 1)
                startActivity(Intent(this@MainEnforced, MainActivity::class.java))
            }
        }
    }
}