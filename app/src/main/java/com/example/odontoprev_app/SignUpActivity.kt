package com.example.odontoprev_app

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SignUpActivity : AppCompatActivity() {

    private var editTextName: EditText? = null
    private var editTextEmail: EditText? = null
    private var editTextTelefone: EditText? = null
    private var editTextCPF: EditText? = null
    private var editTextDayOfBirth: EditText? = null
    private var editTextAddress: EditText? = null
    private var signUpButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        editTextName = findViewById(R.id.name_input);
        editTextEmail = findViewById(R.id.email_input);
        editTextDayOfBirth = findViewById(R.id.birthdate_input);
        editTextCPF = findViewById(R.id.cpf_input);
        editTextTelefone = findViewById(R.id.phone_input);
        editTextAddress = findViewById(R.id.address_input);

    }
}