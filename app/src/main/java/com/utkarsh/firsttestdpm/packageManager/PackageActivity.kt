package com.utkarsh.firsttestdpm.packageManager

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.utkarsh.firsttestdpm.deviceAdmin.DeviceAdmin
import com.utkarsh.firsttestdpm.R


class PackageActivity : AppCompatActivity() {
    companion object{
        const val TAG = "PackageActivity"
    }
    val cn = ComponentName(this,DeviceAdmin::class.java)
    val dpm = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"onCreate() Called")
        setContentView(R.layout.package_layout)
        val packageRecyclerView = findViewById<RecyclerView>(R.id.package_recycler_view)
        val packageList = packageManager.getInstalledApplications(PackageManager.MATCH_DISABLED_COMPONENTS or PackageManager.MATCH_DISABLED_COMPONENTS)
        packageRecyclerView.adapter = ItemAdapter(packageList,cn,dpm)
    }
}