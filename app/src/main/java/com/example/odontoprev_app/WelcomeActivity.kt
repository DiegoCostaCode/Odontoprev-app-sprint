package com.example.odontoprev_app

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.odontoprev_app.utils.api.RemoteApi

class WelcomeActivity : AppCompatActivity() {

    private lateinit var remoteApi: RemoteApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        remoteApi = RemoteApi(this)

        val nameView = findViewById<TextView>(R.id.nameView)

        val idPaciente = intent.getStringExtra("idPaciente")
        Log.d("idPaciente", "Resposta: $idPaciente")

        if (idPaciente != null) {
            remoteApi.getObject("/paciente/$idPaciente", { response ->
                val nomePaciente = response.optString("nome", "Paciente")
                nameView.text = "$nomePaciente!"
            }, { errorMessage ->
                Toast.makeText(this, "Erro ao buscar paciente: $errorMessage", Toast.LENGTH_SHORT).show()
            })
        } else {
            Log.e("WelcomeActivity", "ID do paciente não encontrado.")
            nameView.text = "Bem-vindo, Paciente!"
        }
    }
}