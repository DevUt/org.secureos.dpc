package org.sos.asp.misc

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.runBlocking
import org.sos.asp.R


class Misc : AppCompatActivity() {

    companion object {
        const val TAG = "MiscActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_misc)
//        val cameraSwitch =
//            findViewById<com.google.android.material.switchmaterial.SwitchMaterial>(R.id.camera_switch)
//        var currCameraStatus: Int?
//        runBlocking {
//            currCameraStatus = MiscPrefManager(this@Misc).readEnabled("camera")
//        }
//        if (currCameraStatus != null) {
//            cameraSwitch.isChecked = (currCameraStatus == 1)
//        }
//        cameraSwitch.setOnClickListener {
//            runBlocking {
//                val writeCameraD = MiscPrefManager(this@Misc)
//                writeCameraD.writeEnabled("camera", (cameraSwitch.isChecked).toInt())
//            }
//        }
        //TODO(Make a binding instead of this ugliness)
        val saveButton: Button = findViewById(R.id.misc_save)
        val minWipeTries: com.google.android.material.textfield.TextInputEditText =
            findViewById(R.id.wipe_retries)
        val minPasswordLength: com.google.android.material.textfield.TextInputEditText =
            findViewById(R.id.min_password)
        val minCaps: com.google.android.material.textfield.TextInputEditText =
            findViewById(R.id.min_caps)
        val minLower: com.google.android.material.textfield.TextInputEditText =
            findViewById(R.id.min_lower)
        val minSpecial: com.google.android.material.textfield.TextInputEditText =
            findViewById(R.id.spl_char)
        val minNum: com.google.android.material.textfield.TextInputEditText =
            findViewById(R.id.min_num)
        val vpnOnCheck: CheckBox = findViewById(R.id.vpn_check)

        val vpnLockdown: CheckBox = findViewById(R.id.vpn_lockdown_check)
        val dropdown = findViewById<Spinner>(R.id.vpn_spinner)

        val items = arrayOf(
            "Wireguard",
            "Mullvad",
            "NordVPN",
            "SurfShark",
            "Cyber Ghost",
            "Proton",
            "IP Vanish",
            "StrongVPN",
            "Windscribe",
            "ExpressVPN",
            "OpenVPN"
        )
        dropdown.adapter = ArrayAdapter(
            this, R.layout.spinner_item, items
        )
        var vpnPackageName = "com.wireguard.android"

        runBlocking {
            minWipeTries.setText(
                MiscPrefManager(this@Misc).readEnabled("wipe_retries")?.toString() ?: "7"
            )
            minPasswordLength.setText(
                MiscPrefManager(this@Misc).readEnabled("min_length")?.toString() ?: "20"
            )
            minCaps.setText(
                MiscPrefManager(this@Misc).readEnabled("min_uppercase")?.toString() ?: "1"
            )
            minLower.setText(
                MiscPrefManager(this@Misc).readEnabled("min_lowercase")?.toString() ?: "1"
            )
            minSpecial.setText(
                MiscPrefManager(this@Misc).readEnabled("min_special")?.toString() ?: "1"
            )
            minNum.setText(
                MiscPrefManager(this@Misc).readEnabled("min_number")?.toString() ?: "1"
            )
        }
        runBlocking {
            vpnOnCheck.isChecked = MiscPrefManager(this@Misc).readEnabled("vpn_always") == 2
            if (vpnOnCheck.isChecked) {
                vpnLockdown.isClickable = true
                vpnLockdown.isChecked = MiscPrefManager(this@Misc).readEnabled("vpn_lockdown") == 2
            } else {
                vpnLockdown.isChecked = false
                vpnLockdown.isClickable = false
            }
        }
        vpnOnCheck.setOnClickListener {
            if (vpnOnCheck.isChecked) {
                vpnLockdown.isClickable = true
            } else {
                vpnLockdown.isChecked = false
                vpnLockdown.isClickable = false
            }
        }


        dropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View?, position: Int, id: Long
            ) {
                vpnPackageName = parent.getItemAtPosition(position) as String
                Log.d(TAG, vpnPackageName)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        runBlocking {
            val currentVpnPackageOrder =
                MiscPrefManager(this@Misc).readEnabled("vpn_packageName") ?: 0
            Log.d(TAG, "CurrentVpnPackageOrder $currentVpnPackageOrder")
            dropdown.setSelection(currentVpnPackageOrder)
        }

        saveButton.setOnClickListener {
            if (minWipeTries.text.toString().isBlank() || minPasswordLength.text.toString()
                    .isBlank() || minCaps.text.toString().isBlank() || minLower.text.toString()
                    .isBlank() || minSpecial.text.toString().isBlank() || minNum.text.toString()
                    .isBlank()
            ) {
                Toast.makeText(this, "Text Field Can't be empty", Toast.LENGTH_LONG).show()
            } else {
                runBlocking {
                    val miscPrefManager = MiscPrefManager(this@Misc)
                    miscPrefManager.writeEnabled(
                        "wipe_retries", minWipeTries.text.toString().toInt()
                    )
                    miscPrefManager.writeEnabled(
                        "min_length", minPasswordLength.text.toString().toInt()
                    )
                    miscPrefManager.writeEnabled(
                        "min_uppercase", minCaps.text.toString().toInt()
                    )
                    miscPrefManager.writeEnabled(
                        "min_lowercase", minLower.text.toString().toInt()
                    )
                    miscPrefManager.writeEnabled(
                        "min_special", minSpecial.text.toString().toInt()
                    )
                    miscPrefManager.writeEnabled(
                        "min_number", minNum.text.toString().toInt()
                    )
                    if (vpnOnCheck.isChecked) {
                        miscPrefManager.writeEnabled(
                            "vpn_always", 2
                        )
                    } else {
                        miscPrefManager.writeEnabled(
                            "vpn_always", 1
                        )
                        miscPrefManager.writeEnabled(
                            "vpn_lockdown", 1
                        )
                    }
                    if (vpnLockdown.isClickable && vpnLockdown.isChecked) {
                        miscPrefManager.writeEnabled(
                            "vpn_lockdown", 2
                        )
                    }

                    Log.d(TAG, "Saving vpnPackageName ${getVpnPackageName(vpnPackageName)}")
                    miscPrefManager.writeEnabled(
                        "vpn_packageName", when (getVpnPackageName(vpnPackageName)) {
                            "net.mullvad.mullvadvpn" -> 1
                            "com.nordvpn.android" -> 2
                            "com.surfshark.vpnclient.android" -> 3
                            "de.mobileconcepts.cyberghost" -> 4
                            "ch.protonvpn.android" -> 5
                            "com.ixolit.ipvanish" -> 6
                            "com.strongvpn" -> 7
                            "com.windscribe.vpn" -> 8
                            "com.expressvpn.vpn" -> 9
                            "de.blinkt.openvpn" -> 10
                            else -> 0 // Default is WireGuard
                        }
                    )
                }
            }
            Toast.makeText(this, "Saved Settings", Toast.LENGTH_SHORT).show()
        }
    }

    fun getVpnPackageName(humanReadableName : String): String {
        return when(humanReadableName) {
            "Wireguard" -> "com.wireguard.android"
            "Mullvad" -> "net.mullvad.mullvadvpn"
            "NordVPN" -> "com.nordvpn.android"
            "SurfShark" -> "com.surfshark.vpnclient.android"
            "Cyber Ghost" -> "de.mobileconcepts.cyberghost"
            "Proton" -> "ch.protonvpn.android"
            "IP Vanish" -> "com.ixolit.ipvanish"
            "StrongVPN" -> "com.strongvpn"
            "Windscribe" -> "com.windscribe.vpn"
            "ExpressVPN" -> "com.expressvpn.vpn"
            "OpenVPN" -> "de.blinkt.openvpn"
            else -> "com.wireguard.android" // Default WireGuard
        }
    }

}