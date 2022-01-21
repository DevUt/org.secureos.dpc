package com.utkarsh.firsttestdpm


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.utkarsh.firsttestdpm.packageManager.PackageActivity


class MainActivity : AppCompatActivity() {
    companion object{
        const val TAG = "MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG,"onCreate()")
        val packagePage : Button = findViewById(R.id.go_to_package_page)
        packagePage.setOnClickListener {
            val packageIntent = Intent(this,PackageActivity::class.java)
            this.startActivity(packageIntent)
        }
    }
}