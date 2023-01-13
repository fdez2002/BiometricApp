package com.example.biometricapp

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.biometricapp.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), BiometricAuthCallback {
    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val USER_DATA = "user_data"
        private const val NAME = "name"
        private const val EMAIL = "email"
        private const val PHONE = "phone"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //checkeo capacidades biometricas del dispositivo
        checkBiometricCapability()
        //muestra el prompt dr la biometria
        showBiometricPrompt()
        //rellenamos los datos
        fillUserData()
        binding.buttonSaveData.setOnClickListener { view ->
            //dejamos el boton escuhando eventos de click
            saveUserData()
            Snackbar.make(view, "Datos guardados", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

        }

    }

    override fun onSuccess() {
        binding.layout.visibility = View.VISIBLE
    }

    override fun onError() {
        finish()//si hay muchos intentos fallidos, la aplicacion se cierra
    }

    override fun onNotRecognized() {
        Log.d("MainActivity", "Huella no reconocida")
    }


    private fun checkBiometricCapability() {
       if (!BiometricUtils.isDeviceReady(this)){
           finish()
       }else{
           Toast.makeText(this, "Biometric Disponible", Toast.LENGTH_LONG).show()
       }
    }

    private fun showBiometricPrompt() {
        BiometricUtils.showPromopt(activity = this, callback = this)
    }

    private fun fillUserData() {
        val sharedPreferences: SharedPreferences = getSharedPreferences(USER_DATA, Context.MODE_PRIVATE)
        binding.editTextTextPersonName.setText(sharedPreferences.getString(NAME, ""))
        binding.editTextTextEmailAddress.setText(sharedPreferences.getString(EMAIL, ""))
        binding.editTextNumber.setText(sharedPreferences.getString(PHONE, ""))
    }

    //GUARDADO DE DATOS
    private fun saveUserData() {
        val sharedPreferences: SharedPreferences = getSharedPreferences(USER_DATA, Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.putString(NAME, binding.editTextTextPersonName.text.toString())
        editor.putString(EMAIL, binding.editTextTextEmailAddress.text.toString())
        editor.putString(PHONE, binding.editTextNumber.text.toString())
        editor.apply()

    }




}