package com.utkarsh.firsttestdpm.permissionManagement

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.UserManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.utkarsh.firsttestdpm.R
import com.utkarsh.firsttestdpm.permissionManagement.ItemAdapter
import com.utkarsh.firsttestdpm.permissionManagement.PermissionPrefManager

class PermissionActivity: AppCompatActivity(){
    companion object {
        const val TAG = "PermissionActivity"
    }
   var permissionList = mutableListOf<Pair<String,Int>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() Called")
        setContentView(R.layout.permission_layout)
        val permissionRecyclerView = findViewById<RecyclerView>(R.id.permission_recycler_view)


        permissionList.add(Pair(UserManager.DISALLOW_BLUETOOTH,1))
        permissionList.add(Pair(UserManager.DISALLOW_CONFIG_LOCATION,1))
        permissionList.add(Pair(UserManager.DISALLOW_USB_FILE_TRANSFER,1))
        permissionList.add(Pair(UserManager.DISALLOW_INSTALL_UNKNOWN_SOURCES,1))
        permissionList.add(Pair(UserManager.DISALLOW_INSTALL_UNKNOWN_SOURCES_GLOBALLY,1))
        permissionList.add(Pair(UserManager.DISALLOW_FUN,1))

        permissionRecyclerView.adapter = ItemAdapter(this,permissionList,PermissionPrefManager(this))
    }
}