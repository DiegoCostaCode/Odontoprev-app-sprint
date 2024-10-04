package com.example.odontoprev_app

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.odontoprev_app.Model.Usuario
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SignUpActivity : AppCompatActivity() {

    private var editTextName: EditText? = null
    private var editTextEmail: EditText? = null
    private var editTextTelefone: EditText? = null
    private var editTextCPF: EditText? = null
    private var editTextDayOfBirth: EditText? = null
    private var editTextRua: EditText? = null
    private var editTextCEP: EditText? = null
    private var editTextBairro: EditText? = null
    private var signUpButton: Button? = null

    private fun updateDate(calendar: Calendar) {
        val format = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(format, Locale.ROOT)
        editTextDayOfBirth?.setText(sdf.format(calendar.time))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        editTextName = findViewById(R.id.login_input);
        editTextCPF = findViewById(R.id.cpf_input);
        editTextDayOfBirth = findViewById(R.id.birthdate_input);
        editTextEmail = findViewById(R.id.email_input);
        editTextTelefone = findViewById(R.id.telefone_input);
        editTextRua = findViewById(R.id.rua_input);
        editTextCEP = findViewById(R.id.CEP_input);
        editTextBairro = findViewById(R.id.bairro_input);
        signUpButton = findViewById(R.id.confirm_login_button);

        signUpButton?.setOnClickListener {
            val nome = editTextName?.text.toString()
            val email = editTextEmail?.text.toString()
            val dataNascimento = editTextDayOfBirth?.text.toString()
            val cpf = editTextCPF?.text.toString()
            val telefone = editTextTelefone?.text.toString()
            val rua = editTextRua?.text.toString()
            val bairro = editTextBairro?.text.toString()
            val cep = editTextCEP?.text.toString()
            val numero = 123 // Exemplo de número, você pode obter isso de outro campo

            // Verifica se todos os campos estão preenchidos
            if (nome.isNotEmpty() && email.isNotEmpty() && dataNascimento.isNotEmpty() && cpf.isNotEmpty()) {
                val usuario = Usuario(
                    nome = nome,
                    email = email,
                    dataNascimento = dataNascimento,
                    cpf = cpf,
                    telefone = telefone,
                    rua = rua,
                    bairro = bairro,
                    cep = cep,
                    numero = numero
                )

                val intent = Intent(this, LogInActivity::class.java)
                intent.putExtra("usuario", usuario)
                startActivity(intent)
            } else {

                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }


        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDate(calendar)
        }

        editTextDayOfBirth?.setOnClickListener {
            DatePickerDialog(this, datePicker, calendar.get(Calendar.YEAR), calendar.get(
                Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        }

}