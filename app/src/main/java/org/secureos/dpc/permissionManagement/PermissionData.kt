package org.secureos.dpc.permissionManagement

import android.content.Context
import android.os.UserManager
import kotlinx.coroutines.runBlocking

class PermissionData(val context: Context){
    val permissionList = mutableListOf<Pair<String,Int>>()

    fun populateData() : PermissionData{
        permissionList.add(Pair(UserManager.DISALLOW_BLUETOOTH,2))
        permissionList.add(Pair(UserManager.DISALLOW_BLUETOOTH_SHARING,2))
        permissionList.add(Pair(UserManager.DISALLOW_SHARE_LOCATION,2))
        permissionList.add(Pair(UserManager.DISALLOW_INSTALL_UNKNOWN_SOURCES,2))
        permissionList.add(Pair(UserManager.DISALLOW_INSTALL_UNKNOWN_SOURCES_GLOBALLY,2))
        permissionList.add(Pair(UserManager.DISALLOW_AUTOFILL,2))
        permissionList.add(Pair(UserManager.DISALLOW_FUN,2))
        permissionList.add(Pair(UserManager.DISALLOW_OUTGOING_CALLS,2))
        permissionList.add(Pair(UserManager.DISALLOW_OUTGOING_BEAM,2))
        permissionList.add(Pair(UserManager.DISALLOW_USB_FILE_TRANSFER,2))
        permissionList.add(Pair(UserManager.DISALLOW_CONFIG_BLUETOOTH,2))
        permissionList.add(Pair(UserManager.DISALLOW_NETWORK_RESET,2))
        permissionList.add(Pair(UserManager.DISALLOW_CONFIG_TETHERING,2))
        permissionList.add(Pair(UserManager.DISALLOW_SMS,2))
        permissionList.add(Pair(UserManager.DISALLOW_ADD_USER,2))
        permissionList.add(Pair(UserManager.DISALLOW_REMOVE_USER,2))
        permissionList.add(Pair(UserManager.DISALLOW_AIRPLANE_MODE,2))
        permissionList.add(Pair(UserManager.DISALLOW_DEBUGGING_FEATURES,2))
        permissionList.add(Pair(UserManager.DISALLOW_USER_SWITCH,2))
        permissionList.add(Pair(UserManager.DISALLOW_CONFIG_CELL_BROADCASTS,2))
        permissionList.add(Pair(UserManager.DISALLOW_MODIFY_ACCOUNTS,1))
        permissionList.add(Pair(UserManager.DISALLOW_FACTORY_RESET,2))
        permissionList.add(Pair(UserManager.DISALLOW_CONFIG_LOCATION,2))


        // DEPRECATED
//        permissionList.add(Pair(UserManager.DISALLOW_ADD_MANAGED_PROFILE,2))
        permissionList.add(Pair(UserManager.DISALLOW_APPS_CONTROL,1))
        // THIS WOULD JUST DISABLE ALL INSTALLATION
//        permissionList.add(Pair(UserManager.DISALLOW_INSTALL_APPS,2))
        permissionList.add(Pair(UserManager.DISALLOW_UNINSTALL_APPS,1))
        permissionList.add(Pair(UserManager.DISALLOW_SAFE_BOOT,2))
        permissionList.add(Pair(UserManager.DISALLOW_SYSTEM_ERROR_DIALOGS,2))
        permissionList.add(Pair(UserManager.DISALLOW_MOUNT_PHYSICAL_MEDIA,2))
        permissionList.add(Pair(UserManager.DISALLOW_CONTENT_SUGGESTIONS,2))
        permissionList.add(Pair(UserManager.DISALLOW_CONFIG_SCREEN_TIMEOUT,2))
        permissionList.add(Pair(UserManager.DISALLOW_AMBIENT_DISPLAY,2))
        permissionList.add(Pair(UserManager.DISALLOW_CONFIG_VPN,2))
        return this
    }
    fun returnData() : MutableList<Pair<String,Int>>{
        return permissionList
    }


    /*
     * We only Enforce data when it has already been not set
     * All other actions that are done using the switch
     * are handled by the switch's onClickListener
     * This func is only for non-set data, and should be called once
     * calling it  on any object after its first call on any instance
     * wouldn't have any affect
     */
    fun enforceData() : PermissionData{
        val permissionPref = PermissionPrefManager(context)
        for(perms in permissionList){
            runBlocking {
                if(permissionPref.readPermEnabled(perms.first) == 0)
                    permissionPref.writePermEnabled(perms.first,perms.second)
            }
        }
        return this
    }
}
