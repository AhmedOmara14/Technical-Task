package com.task.football_league.presentation.favorite_competitions.auth

import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.task.football_league.R
import com.task.football_league.common.HelperClass
import com.task.football_league.databinding.FragmentAuthBinding
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey


class AuthFragment : Fragment(), AuthenticationInterface {
    private lateinit var fm: FingerprintManager
    private lateinit var km: KeyguardManager
    private lateinit var keyStore: KeyStore
    private lateinit var keyGenerator: KeyGenerator
    private var KEY_NAME = "my_key"
    private lateinit var cipher: Cipher
    private lateinit var cryptoObject: FingerprintManager.CryptoObject
    private var binding: FragmentAuthBinding? = null
    private var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthBinding.inflate(inflater, container, false)
        binding!!.lifecycleOwner = this
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponent()
        if (!km.isKeyguardSecure) {
            HelperClass.showToast(context, "Lock Screen Security not enabled in Settings.")
            return
        }
        if (!fm.hasEnrolledFingerprints()) {
            HelperClass.showToast(context, "Register atleast one fingerprint in Settings.")
            return
        }
        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    android.Manifest.permission.USE_FINGERPRINT
                )
            } != PackageManager.PERMISSION_GRANTED) {
            activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(android.Manifest.permission.USE_FINGERPRINT),
                    111
                )
            }
        } else
            validateFingerPrint()
    }

    private fun initComponent() {
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        km = activity?.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        fm = activity?.getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            validateFingerPrint()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun validateFingerPrint() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyGenerator =
                KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            keyStore.load(null)
            keyGenerator.init(
                KeyGenParameterSpec.Builder(
                    KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build()
            )
            keyGenerator.generateKey()

        } catch (e: Exception) {
        }
        if (initCipher()) {
            cipher.let {
                cryptoObject = FingerprintManager.CryptoObject(it)
            }
        }
        var helper = FingerPrintHelper(context,this)
        helper.startAuth(fm, cryptoObject)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initCipher(): Boolean {
        try {
            cipher = Cipher.getInstance(
                KeyProperties.KEY_ALGORITHM_AES + "/"
                        + KeyProperties.BLOCK_MODE_CBC + "/"
                        + KeyProperties.ENCRYPTION_PADDING_PKCS7
            )
        } catch (e: Exception) {
        }

        try {
            keyStore.load(null)
            val key = keyStore.getKey(KEY_NAME, null) as SecretKey
            cipher.init(Cipher.ENCRYPT_MODE, key)
            return true
        } catch (e: java.lang.Exception) {
            return false
        }
    }

    override fun confirmAuth() {
        navController?.navigate(R.id.action_authFragment_to_favoriteCompetitionsFragment)
    }
}