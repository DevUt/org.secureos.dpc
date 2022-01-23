package org.secureos.dpc.packageManagement

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import org.secureos.dpc.R

class PackageActivity : AppCompatActivity() {
    companion object {
        const val TAG = "PackageActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() Called")
        setContentView(R.layout.package_layout)
        val packageList = PackageData(this,PackageManager.MATCH_DISABLED_COMPONENTS,true).returnData()
        val packageRecyclerView = findViewById<RecyclerView>(R.id.package_recycler_view)
        packageRecyclerView.adapter = ItemAdapter(packageList,
            PackagePrefManager(this),packageManager)
        val packageRefresh = findViewById<androidx.swiperefreshlayout.widget.SwipeRefreshLayout>(R.id.package_refresh_layout)
        packageRefresh.setOnRefreshListener {
            recreate()
            packageRefresh.isRefreshing = false
        }
    }
}