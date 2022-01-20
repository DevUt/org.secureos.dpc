package com.utkarsh.firsttestdpm

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import android.widget.TextView
import com.utkarsh.firsttestdpm.DeviceAdmin.device_admin

const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val cn = ComponentName(this,device_admin::class.java)
        val dpm = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        val disableSwitch : Switch = findViewById(R.id.disable_switch)

        disableSwitch.setOnClickListener{
            if(disableSwitch.isChecked){
                dpm.setApplicationHidden(cn,"com.whatsapp",true)
            }else{
                dpm.setApplicationHidden(cn,"com.whatsapp",false)
            }
        }
    }
}