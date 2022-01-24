package org.secureos.dpc


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import org.secureos.dpc.R
import org.secureos.dpc.misc.Misc
import org.secureos.dpc.packageManagement.PackageActivity
import org.secureos.dpc.permissionManagement.PermissionActivity
import org.secureos.dpc.permissionManagement.PermissionData


class MainActivity : AppCompatActivity() {
    companion object{
        const val TAG = "MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate()")
        val packagePage : Button = findViewById(R.id.go_to_package_page)
        packagePage.setOnClickListener {
            val packageIntent = Intent(this, PackageActivity::class.java)
            this.startActivity(packageIntent)
        }
        val permissionPage: Button = findViewById(R.id.go_to_permission_page)
        permissionPage.setOnClickListener {
            val permissionIntent = Intent(this, PermissionActivity::class.java)
            this.startActivity(permissionIntent)
        }
        PermissionData(this).populateData().enforceData()
        val miscPage: Button = findViewById(R.id.go_to_misc_page)
        miscPage.setOnClickListener{
            val  miscIntent = Intent(this, Misc::class.java)
            this.startActivity(miscIntent)
        }

    }
}



