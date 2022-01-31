package org.secureos.dpc.packageManagement

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.runBlocking
import org.secureos.dpc.R

class ItemAdapter(
    private val packageList: MutableList<ApplicationInfo>,
    private val packagePref: PackagePrefManager,
    private val pm: PackageManager,
    private val unBannablePackageList: List<String>
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val packageName: TextView = view.findViewById(R.id.package_name)
        val disableCheck: CheckBox = view.findViewById(R.id.disable_check)
        val packageIcon: ImageView = view.findViewById(R.id.package_icon)
    }
    companion object{
        val TAG = "PackageItem"
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.package_list_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = packageList[position]
        holder.packageName.text = item.loadLabel(pm).toString()
        holder.packageIcon.setImageDrawable(item.loadIcon(pm))
        if(unBannablePackageList.contains(item.packageName)){
            Log.d(TAG,"unbannable " + item.packageName)
            holder.disableCheck.isChecked = false
            holder.disableCheck.isClickable = false
            return
        }
        holder.disableCheck.isChecked = item.enabled
        // 0-> not set 1-> enabled 2-> disabled
        runBlocking {
            val itemRead = packagePref.readEnabled(item.packageName)
            if (itemRead != null)
                Log.i(TAG, item.packageName + " " + itemRead.toString())
            if (itemRead == 0 || itemRead == null) {
                holder.disableCheck.isChecked = !item.enabled
            } else holder.disableCheck.isChecked = itemRead == 2
        }
        // 0-> not set 1-> enabled 2-> disabled
        holder.disableCheck.setOnClickListener {
            runBlocking {
                if (holder.disableCheck.isChecked) {
                    packagePref.writeEnabled(item.packageName, 2)
                } else {
                    packagePref.writeEnabled(item.packageName, 1)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return packageList.size
    }
}