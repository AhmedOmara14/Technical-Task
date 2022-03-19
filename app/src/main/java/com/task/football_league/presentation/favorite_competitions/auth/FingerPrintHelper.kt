package com.task.football_league.presentation.favorite_competitions.auth

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.CancellationSignal
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat

@RequiresApi(Build.VERSION_CODES.M)
class FingerPrintHelper(
    private val context: Context?,var listener:AuthenticationInterface?=null
):FingerprintManager.AuthenticationCallback() {
    private lateinit var cancellationSignal:CancellationSignal
    fun startAuth(manager:FingerprintManager,cryptoObject: FingerprintManager.CryptoObject)
    {
        cancellationSignal= CancellationSignal()
        if(context?.let { ActivityCompat.checkSelfPermission(it,android.Manifest.permission.USE_FINGERPRINT) } !=PackageManager.PERMISSION_GRANTED){
            return
        }
        manager.authenticate(cryptoObject,cancellationSignal,0,this,null)
    }

    override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
        super.onAuthenticationError(errorCode, errString)
        Toast.makeText(context,"Authentication Error",Toast.LENGTH_SHORT).show()
    }

    override fun onAuthenticationSucceeded(result: FingerprintManager.AuthenticationResult?) {
        super.onAuthenticationSucceeded(result)
        Toast.makeText(context,"Authentication Succeeded",Toast.LENGTH_SHORT).show()
        listener?.confirmAuth()
    }

    override fun onAuthenticationFailed() {
        super.onAuthenticationFailed()
        Toast.makeText(context,"Authentication Failed",Toast.LENGTH_SHORT).show()
    }
}