package com.example.biometricapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.biometricapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

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
        binding.buttonSaveData.setOnClickListener {
            //dejamos el boton escuhando eventos de click
            saveUserData()
        }

    }


    private fun checkBiometricCapability() {
       if (!BiometricUtils.isDeviceReady(this)){
           finish()
       }else{
           Toast.makeText(this, "Biometric Disponible", Toast.LENGTH_LONG).show()
       }
    }

    private fun showBiometricPrompt() {
    }

    private fun fillUserData() {
    }

    private fun saveUserData() {
    }


}