package com.utkarsh.firsttestdpm.packageManagement

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.utkarsh.firsttestdpm.R
import com.utkarsh.firsttestdpm.deviceAdmin.DeviceAdmin

class PackageActivity : AppCompatActivity() {
    companion object {
        const val TAG = "PackageActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cn = ComponentName(this, DeviceAdmin::class.java)
        val dpm = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        Log.d(TAG, "onCreate() Called")
        setContentView(R.layout.package_layout)
        val packageRecyclerView = findViewById<RecyclerView>(R.id.package_recycler_view)
        val pm = packageManager
        val packageList =
            pm.getInstalledApplications(PackageManager.MATCH_DISABLED_COMPONENTS or PackageManager.MATCH_DISABLED_COMPONENTS)
        packageList.sortBy { it.packageName }
        packageRecyclerView.adapter = ItemAdapter(packageList,pm)
    }
}