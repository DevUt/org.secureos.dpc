package org.secureos.dpc.permissionManagement

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import org.secureos.dpc.R

class PermissionActivity: AppCompatActivity(){
    companion object {
        const val TAG = "PermissionActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() Called")
        setContentView(R.layout.permission_layout)
        val permissionRecyclerView = findViewById<RecyclerView>(R.id.permission_recycler_view)
        val permissionD = PermissionData(this)
        permissionD.populateData()
        permissionRecyclerView.adapter = ItemAdapter(this,permissionD, PermissionPrefManager(this))
        val permissionRefresh = findViewById<androidx.swiperefreshlayout.widget.SwipeRefreshLayout>(R.id.permission_refresh_layout)
        permissionRefresh.setOnRefreshListener {
            recreate()
            permissionRefresh.isRefreshing = false
        }
    }

}