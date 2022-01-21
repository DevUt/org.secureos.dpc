package com.utkarsh.firsttestdpm.packageManagement

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.utkarsh.firsttestdpm.R

class ItemAdapter(
    private val packageList: MutableList<ApplicationInfo>,
    private val cn: ComponentName,
    private val dpm: DevicePolicyManager,
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val packageName: TextView = view.findViewById(R.id.package_name)
        val disableSwitch: SwitchMaterial = view.findViewById(R.id.disable_switch)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.package_list_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = packageList[position]
        if(item.enabled){
            if(!holder.disableSwitch.isChecked)
                    holder.disableSwitch.toggle()
        }
        holder.packageName.text = item.packageName
    }

    override fun getItemCount(): Int {
        return packageList.size
    }
}