package org.secureos.dpc.permissionManagement

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.utkarsh.firsttestdpm.R

class PermissionActivity: AppCompatActivity(){
    companion object {
        const val TAG = "PermissionActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() Called")
        setContentView(R.layout.permission_layout)
        val permissionRecyclerView = findViewById<RecyclerView>(R.id.permission_recycler_view)
        val permissionD = PermissionData()
        permissionD.populateData()
        permissionRecyclerView.adapter = ItemAdapter(this,permissionD, PermissionPrefManager(this))
    }

}