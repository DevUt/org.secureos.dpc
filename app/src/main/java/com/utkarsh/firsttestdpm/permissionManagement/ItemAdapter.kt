package com.utkarsh.firsttestdpm.permissionManagement

import android.Manifest
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
import com.utkarsh.firsttestdpm.permissionManagement.PermissionPrefManager
import kotlinx.coroutines.runBlocking

class ItemAdapter(
    private val context: Context,
    private val permissionList: MutableList<Pair<String,Int>>,
    private val permissionPref: PermissionPrefManager,
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val permissionName: TextView = view.findViewById(R.id.permission_name)
        val disableSwitch: SwitchMaterial = view.findViewById(R.id.disable_switch)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.permission_list_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = permissionList[position]
        holder.permissionName.text = item.first
        runBlocking {
            val itemRead = permissionPref.readPermEnabled(item.first)
            if(itemRead == null || itemRead== 0 || itemRead == 1){
                holder.disableSwitch.isChecked = true
                holder.disableSwitch.setBackgroundColor(Color.CYAN)
            }else{
                holder.disableSwitch.isChecked = false
                holder.disableSwitch.setBackgroundColor(Color.YELLOW)
            }

        }

    }

    override fun getItemCount(): Int {
        return permissionList.size
    }
}