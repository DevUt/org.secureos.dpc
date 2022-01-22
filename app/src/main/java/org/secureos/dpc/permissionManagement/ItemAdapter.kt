package org.secureos.dpc.permissionManagement

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.coroutines.runBlocking
import org.secureos.dpc.R

class ItemAdapter(
    private val context: Context,
    private val permissionD: PermissionData,
    private val permissionPref: PermissionPrefManager,
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    val permissionList = permissionD.returnData()
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
        holder.disableSwitch.setOnClickListener {
            if(holder.disableSwitch.isChecked){
                runBlocking {
                    permissionPref.writePermEnabled(item.first,1)
                    holder.disableSwitch.setBackgroundColor(Color.CYAN)
                }
            }else{
                runBlocking {
                    permissionPref.writePermEnabled(item.first,2)
                    holder.disableSwitch.setBackgroundColor(Color.YELLOW)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return permissionList.size
    }
}