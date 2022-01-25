package org.secureos.dpc.permissionManagement

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.runBlocking
import org.secureos.dpc.R

class ItemAdapter(
    private val context: Context,
    private val permissionD: PermissionData,
    private val permissionPref: PermissionPrefManager,
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    private val permissionList = permissionD.returnData()

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val permissionName: TextView = view.findViewById(R.id.permission_name)
        val disableCheck: CheckBox = view.findViewById(R.id.disable_check)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.permission_list_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = permissionList[position]
        holder.permissionName.text = item.first
        runBlocking {
            val itemRead = permissionPref.readPermEnabled(item.first)
            holder.disableCheck.isChecked = !(itemRead == null || itemRead == 0 || itemRead == 1)

        }
        holder.disableCheck.setOnClickListener {
            if (holder.disableCheck.isChecked) {
                runBlocking {
                    permissionPref.writePermEnabled(item.first, 1)
                }
            } else {
                runBlocking {
                    permissionPref.writePermEnabled(item.first, 2)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return permissionList.size
    }
}