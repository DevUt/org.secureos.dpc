package com.utkarsh.firsttestdpm.packageManagement

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.utkarsh.firsttestdpm.R

class PackageActivity : AppCompatActivity() {
    companion object {
        const val TAG = "PackageActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() Called")
        setContentView(R.layout.package_layout)
        val packageList =
            packageManager.getInstalledApplications(PackageManager.MATCH_DISABLED_COMPONENTS or PackageManager.MATCH_DISABLED_COMPONENTS)
        val packageRecyclerView = findViewById<RecyclerView>(R.id.package_recycler_view)
        packageList.sortBy { it.packageName }
        packageRecyclerView.adapter = ItemAdapter(this,packageList,packageManager,PackagePrefManager(this),lifecycleScope)
    }
}