package com.utkarsh.firsttestdpm.permissionManagement

import android.os.UserManager

class PermissionData {
    val permissionList = mutableListOf<Pair<String,Int>>()

    fun populateData(){
        permissionList.add(Pair(UserManager.DISALLOW_BLUETOOTH,2))
        permissionList.add(Pair(UserManager.DISALLOW_BLUETOOTH_SHARING,2))
        permissionList.add(Pair(UserManager.DISALLOW_SHARE_LOCATION,2))
        permissionList.add(Pair(UserManager.DISALLOW_INSTALL_UNKNOWN_SOURCES,2))
        permissionList.add(Pair(UserManager.DISALLOW_INSTALL_UNKNOWN_SOURCES_GLOBALLY,2))
        permissionList.add(Pair(UserManager.DISALLOW_AUTOFILL,2))
        permissionList.add(Pair(UserManager.DISALLOW_FUN,2))
        permissionList.add(Pair(UserManager.DISALLOW_OUTGOING_CALLS,2))
        permissionList.add(Pair(UserManager.DISALLOW_USB_FILE_TRANSFER,2))
        permissionList.add(Pair(UserManager.DISALLOW_CONFIG_BLUETOOTH,2))
        permissionList.add(Pair(UserManager.DISALLOW_NETWORK_RESET,2))
        permissionList.add(Pair(UserManager.DISALLOW_CONFIG_TETHERING,2))
        permissionList.add(Pair(UserManager.DISALLOW_SMS,2))
        permissionList.add(Pair(UserManager.DISALLOW_ADD_USER,2))
        permissionList.add(Pair(UserManager.DISALLOW_REMOVE_USER,2))
        permissionList.add(Pair(UserManager.DISALLOW_AIRPLANE_MODE,2))
        permissionList.add(Pair(UserManager.DISALLOW_DEBUGGING_FEATURES,2))
    }
    fun returnData() : MutableList<Pair<String,Int>>{
        return permissionList
    }
}