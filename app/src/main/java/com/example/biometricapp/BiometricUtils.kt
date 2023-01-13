package com.example.biometricapp

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricManager.BIOMETRIC_SUCCESS
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat


interface BiometricAuthCallback {
    fun onSuccess()
    fun onError()
    fun onNotRecognized()
}
/**
 * Se definen los metodos que sean relativos a todo lo que se operaciones de biometria
 * ej: metodo que diga que el dispositivo este listo para usar biometria
 */
object BiometricUtils {

    //nos dice si el dispositivo permite biometria
    fun isDeviceReady(context: Context): Boolean = getCapability(context) == BIOMETRIC_SUCCESS


    //devulve si tiene capacidad de biometria
    private fun getCapability(context: Context): Int =
        BiometricManager.from(context).canAuthenticate(BIOMETRIC_WEAK)

    //mostramos el promt de geometria
    fun showPromopt(
        title: String ="Autenticacion Biometrica",
        subtitle: String ="Introduce tus credenciales",
        description: String= "Introduce tus huella para verificar que eres tu",
        cancelButton: String = "Cancelar",
        activity: AppCompatActivity,
        callback: BiometricAuthCallback
    ){
        val promptInfo =
            BiometricPrompt.PromptInfo.Builder()
                .setTitle(title)
                .setSubtitle(subtitle)
                .setDescription(description)
                .setAllowedAuthenticators(BIOMETRIC_WEAK)
                .setNegativeButtonText(cancelButton)
                .build()
        val prompt = initPrompt(activity, callback)
        prompt.authenticate(promptInfo)

    }
    private fun initPrompt(activity: AppCompatActivity, callback: BiometricAuthCallback):BiometricPrompt{
        val executor = ContextCompat.getMainExecutor(activity)
        val authenticationCallback = object : BiometricPrompt.AuthenticationCallback(){

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                callback.onSuccess()
            }
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                callback.onError()
            }
            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                callback.onNotRecognized()
            }
        }
        return BiometricPrompt(activity, executor, authenticationCallback)

    }



}