package com.ussd.sam.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import com.ussd.sam.R
import com.ussd.sam.model.USSDResponses
import com.ussd.sam.service.CustomAccessibilityService
import com.ussd.sam.utils.BaseUtils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : PermissionActivity() {

    companion object {

        const val TAG: String = "MainActivity"
    }

    private var serviceIntent: Intent? = null
    private var notifyBroadcastReceiver: BroadcastReceiver? = null
    private var selectedUssd: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        serviceIntent = Intent(applicationContext, CustomAccessibilityService::class.java)
        startService(serviceIntent)
        registerNotificationReceiver()

        sendBtn!!.setOnClickListener {

            selectedUssd = input!!.text.toString()
            handlePermissions()

        }
    }

    private fun handlePermissions() {
        if (!isPermissionsGranted)
            askPermissions()
        else
            handleAccessibilityServices()
    }


    private fun handleAccessibilityServices() {
        if (!isAccessibilityGranted) {
            askAccessibilityPermission()
        } else {
            BaseUtils.sendUSSDRequest(this, selectedUssd)
        }
    }

    override fun onPermissionGranted(permissions: Array<out String>?, grantResults: IntArray?) {
        handlePermissions()
    }

    private fun registerNotificationReceiver() {
        notifyBroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {

                val extra = intent.getParcelableExtra(USSDResponses.TAG) as USSDResponses

                when {

                    extra.isInputResponse -> {

                        Log.d(TAG, "isInputResponse ${extra.isInputResponse}")
                        Log.d(TAG, "response ${extra.inputResponse}")

                    }
                    extra.isNoticeResponse -> {

                        Log.d(TAG, "isNoticeResponse ${extra.isNoticeResponse}")
                        Log.d(TAG, "response ${extra.noticeResponse}")
                    }
                }

            }
        }

        registerReceiver(notifyBroadcastReceiver, IntentFilter("com.ussd.sam.action.REFRESH"))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == BaseUtils.SETTINGS_REQUEST_CODE) {
            if (isPermissionsGranted) {
                handleAccessibilityServices()
            }

        } else if (requestCode == BaseUtils.ACCESSIBILITY_REQUEST_CODE) {
            handleAccessibilityServices()
        }
    }
}
