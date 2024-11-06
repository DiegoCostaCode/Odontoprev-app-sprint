package com.example.odontoprev_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.odontoprev_app.utils.api.RemoteApi
import org.json.JSONArray
import org.json.JSONObject

class LogInActivity : AppCompatActivity() {

    private var loginButton: Button? = null
    private var emailEcpfLogin: EditText? = null
    private lateinit var remoteApi: RemoteApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_log_in)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        remoteApi = RemoteApi(this)
        emailEcpfLogin = findViewById(R.id.name_input)
        loginButton = findViewById(R.id.confirm_sign_button)

        loginButton?.setOnClickListener {
            val emailEcpf = emailEcpfLogin?.text.toString()
            buscarPaciente(emailEcpf)
        }
    }

    private fun buscarPaciente(emailEcpf: String) {
        remoteApi.getArray("/paciente", { pacientes ->
            var pacienteEncontrado: JSONObject? = null

            for (i in 0 until pacientes.length()) {
                val paciente = pacientes.getJSONObject(i)
                val email = paciente.optString("email", "")
                val cpf = paciente.optString("cpf", "")

                // Verifica se o input do usuário corresponde ao email ou CPF do paciente
                if (emailEcpf.equals(email, ignoreCase = true) || emailEcpf.equals(cpf, ignoreCase = true)) {
                    pacienteEncontrado = paciente
                    break
                }
            }

            if (pacienteEncontrado != null) {
                Toast.makeText(this, "Login bem-sucedido para: ${pacienteEncontrado.optString("nome")}", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginSucess::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Paciente não encontrado.", Toast.LENGTH_SHORT).show()
            }
        }, { errorMessage ->
            Toast.makeText(this, "Erro ao buscar pacientes: $errorMessage", Toast.LENGTH_SHORT).show()
        })
    }
}