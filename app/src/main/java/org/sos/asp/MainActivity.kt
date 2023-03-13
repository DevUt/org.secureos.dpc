package org.sos.asp


import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.runBlocking
import org.sos.asp.deviceAdmin.DeviceAdmin
import org.sos.asp.misc.Misc
import org.sos.asp.misc.MiscData
import org.sos.asp.misc.MiscPrefManager
import org.sos.asp.packageManagement.PackageActivity
import org.sos.asp.packageManagement.PackageData
import org.sos.asp.permissionManagement.PermissionActivity
import org.sos.asp.permissionManagement.PermissionData


class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate()")
        val packagePage: Button = findViewById(R.id.go_to_package_page)
        packagePage.setOnClickListener {
            val packageIntent = Intent(this, PackageActivity::class.java)
            this.startActivity(packageIntent)
        }
        val miscPrefManager = MiscPrefManager(this)
        runBlocking {
            if (DeviceAdmin().isAdmin(this@MainActivity)) {
                if (miscPrefManager.readEnabled("enforced") == 2) {
                    val enforcedIntent = Intent(this@MainActivity, MainEnforced::class.java)
                    this@MainActivity.startActivity(enforcedIntent)
                    finish()
                }
            }
        }
        val permissionPage: Button = findViewById(R.id.go_to_permission_page)
        permissionPage.setOnClickListener {
            val permissionIntent = Intent(this, PermissionActivity::class.java)
            this.startActivity(permissionIntent)
        }
        PermissionData(this).populateData().enforceData()
        PackageData(this, PackageManager.MATCH_DISABLED_COMPONENTS, true).enforceDefaults()
        MiscData(this).enforceDefaults()
        val miscPage: Button = findViewById(R.id.go_to_misc_page)
        miscPage.setOnClickListener {
            val miscIntent = Intent(this, Misc::class.java)
            this.startActivity(miscIntent)
        }
        val logoEnable: ImageView = findViewById(R.id.main_logo)
        logoEnable.setOnClickListener {
            val deviceObj = DeviceAdmin()
            if (deviceObj.isAdmin(this)) {
                deviceObj.enforcePolicy(this)
            }
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        val miscPrefManager = MiscPrefManager(this)
        runBlocking {
            if (DeviceAdmin().isAdmin(this@MainActivity)) {
                if (miscPrefManager.readEnabled("enforced") == 2) {
                    val enforcedIntent = Intent(this@MainActivity, MainEnforced::class.java)
                    this@MainActivity.startActivity(enforcedIntent)
                    finish()
                }
            }
        }
    }
}



