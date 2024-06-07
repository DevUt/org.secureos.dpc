package org.sos.asp.deviceAdmin

import android.app.admin.PolicyUpdateReceiver
import android.app.admin.PolicyUpdateResult
import android.app.admin.TargetUser
import android.content.Context
import android.os.Bundle
import android.util.Log

class PolicyUpdates : PolicyUpdateReceiver() {
    companion object {
        const val TAG = "PolicyStatus"
    }

    override fun onPolicyChanged(
        context: Context,
        policyIdentifier: String,
        additionalPolicyParams: Bundle,
        targetUser: TargetUser,
        policyUpdateResult: PolicyUpdateResult
    ) {
        super.onPolicyChanged(
            context,
            policyIdentifier,
            additionalPolicyParams,
            targetUser,
            policyUpdateResult
        )

        if(policyUpdateResult.resultCode
            == PolicyUpdateResult.RESULT_POLICY_SET){
            Log.d(TAG, "$policyIdentifier has been changed")
        }else{
            Log.d(TAG, "$policyIdentifier has not been set due to  code $policyUpdateResult")
        }
    }

    override fun onPolicySetResult(
        context: Context,
        policyIdentifier: String,
        additionalPolicyParams: Bundle,
        targetUser: TargetUser,
        policyUpdateResult: PolicyUpdateResult
    ) {
        super.onPolicySetResult(
            context,
            policyIdentifier,
            additionalPolicyParams,
            targetUser,
            policyUpdateResult
        )
        if(policyUpdateResult.resultCode
            == PolicyUpdateResult.RESULT_POLICY_SET){
            Log.d(TAG, "$policyIdentifier has been changed")
        }else{
            Log.d(TAG, "$policyIdentifier has not been set due to  code $policyUpdateResult")
        }
    }

}