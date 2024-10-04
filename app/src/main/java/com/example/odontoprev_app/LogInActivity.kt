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

class LogInActivity : AppCompatActivity() {

    private var loginButton: Button? = null
    private var emailEcpfLogin: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_log_in)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val userEmail = intent.getStringExtra("email")
        val userCpf = intent.getStringExtra("cpf")

        emailEcpfLogin = findViewById(R.id.login_input)
        loginButton = findViewById(R.id.confirm_login_button)

        var emailEcpf = emailEcpfLogin?.text.toString()

        loginButton?.setOnClickListener {
            val emailEcpf = emailEcpfLogin?.text.toString()

            // Verifica se o email e a senha fornecidos correspondem ao cadastro
            if () {
                Toast.makeText(this, "Login bem-sucedido! Bem-vindo(a), $userName!", Toast.LENGTH_SHORT).show()
                // Aqui você pode redirecionar para outra tela ou exibir informações
                val intent = Intent(this, TokenActivity::class.java)
                intent.putExtra("name", userName)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Email ou senha incorretos. Tente novamente.", Toast.LENGTH_SHORT).show()
            }

        }
    }
}