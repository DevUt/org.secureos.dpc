package com.utkarsh.firsttestdpm.packageManagement

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.utkarsh.firsttestdpm.R
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ItemAdapter(
    private val context: Context,
    private val packageList: MutableList<ApplicationInfo>,
    private val pm: PackageManager,
    private val packagePref: PackagePrefManager,
    private val lifecycleScope: LifecycleCoroutineScope,
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val packageName: TextView = view.findViewById(R.id.package_name)
        val disableSwitch: SwitchMaterial = view.findViewById(R.id.disable_switch)
        val packageIcon : ImageView = view.findViewById(R.id.package_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.package_list_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = packageList[position]
        holder.disableSwitch.isChecked = item.enabled
        holder.packageName.text = item.packageName
        holder.packageIcon.setImageDrawable(item.loadIcon(pm))
        // 0-> not set 1-> enabled 2-> disabled
        runBlocking{
            if(packagePref.readEnabled(item.packageName) == 2){
                holder.disableSwitch.setBackgroundColor(Color.YELLOW)
                holder.disableSwitch.isChecked = false
            }else{
                holder.disableSwitch.isChecked = true
            }
        }
        // 0-> not set 1-> enabled 2-> disabled
        holder.disableSwitch.setOnClickListener {
            runBlocking {
                if(holder.disableSwitch.isChecked){
                    packagePref.writeEnabled(item.packageName,1)
                }else{
                    packagePref.writeEnabled(item.packageName,2)
                    holder.disableSwitch.setBackgroundColor(Color.YELLOW)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return packageList.size
    }
}