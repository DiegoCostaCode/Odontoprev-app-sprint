package com.example.odontoprev_app

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.odontoprev_app.model.Paciente
import com.example.odontoprev_app.utils.api.RemoteApi
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SignUpActivity : AppCompatActivity() {

    private lateinit var remoteApi: RemoteApi
    private var editTextDayOfBirth: EditText? = null

    private fun updateDate(calendar: Calendar) {
        val format = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(format, Locale.ROOT)
        editTextDayOfBirth?.setText(sdf.format(calendar.time))
    }

    private fun cadastrarEndereco(idPaciente: String) {
        val editTextRua = findViewById<EditText>(R.id.ruaInput)
        val editTextNumero = findViewById<EditText>(R.id.numeroInput)
        val editTextComplemento = findViewById<EditText>(R.id.complementoInput)
        val editTextCep = findViewById<EditText>(R.id.cepInput)

        val cep = editTextCep.text.toString()

        remoteApi.getCidadeEEstadoFromCep(cep) { cidade, estado ->
            if (cidade != null && estado != null) {
                val enderecoJson = JSONObject().apply {
                    put("rua", editTextRua.text.toString())
                    put("complemento", editTextComplemento.text.toString())
                    put("numero", editTextNumero.text.toString().toInt())
                    put("cep", cep)
                    put("cidade", JSONObject().apply {
                        put("cidade", cidade)
                        put("estado", JSONObject().apply {
                            put("nome", estado)
                            put("pais", JSONObject().apply {
                                put("nome", "BRASIL")
                            })
                        })
                    })
                }

                remoteApi.post("/endereco/paciente/$idPaciente", enderecoJson, { response ->
                    Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                    Log.d("SignUpActivity", "Resposta: $response")


                    val intent = Intent(this, WelcomeActivity::class.java)
                    intent.putExtra("idPaciente", idPaciente) // Passando o ID do paciente
                    startActivity(intent)
                    finish()

                }, { errorMessage ->
                    Toast.makeText(this, "Erro ao cadastrar endereço: $errorMessage", Toast.LENGTH_SHORT).show()
                })
            } else {
                Toast.makeText(this, "Erro ao buscar cidade e estado pelo CEP", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Inicialize o remoteApi
        remoteApi = RemoteApi(this)

        val editTextName = findViewById<EditText>(R.id.nameInput)
        val editTextCPF = findViewById<EditText>(R.id.cpf_input)
        editTextDayOfBirth = findViewById(R.id.birthdate_input)
        val editTextEmail = findViewById<EditText>(R.id.email_input)
        val editTextTelefone = findViewById<EditText>(R.id.telefone_input)
        val editTextRua = findViewById<EditText>(R.id.ruaInput)
        val editTextNumero = findViewById<EditText>(R.id.numeroInput)
        val editTextComplemento = findViewById<EditText>(R.id.complementoInput)
        val editTextCep = findViewById<EditText>(R.id.cepInput)
        val signUpButton = findViewById<Button>(R.id.confirm_sign_button)

        signUpButton.setOnClickListener {
            val paciente = Paciente().apply {
                nome = editTextName.text.toString()
                cpf = editTextCPF.text.toString()
                dataNascimento = editTextDayOfBirth?.text.toString()
                email = editTextEmail.text.toString()
                telefone = editTextTelefone.text.toString()
            }

            // Convertendo o objeto Paciente para JSON
            val pacienteJson = JSONObject().apply {
                put("nome", paciente.nome)
                put("cpf", paciente.cpf)
                put("dataNascimento", paciente.dataNascimento)
                put("email", paciente.email)
                put("telefone", paciente.telefone)
            }

            remoteApi.post("/paciente", pacienteJson, { response ->
                val idPaciente = response.getString("id")
                cadastrarEndereco(idPaciente)
            }, { errorMessage ->
                Toast.makeText(this, "Erro ao cadastrar paciente: $errorMessage", Toast.LENGTH_SHORT).show()
            })
        }

        // Configurando o DatePicker para o campo de data
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDate(calendar)
        }

        editTextDayOfBirth?.setOnClickListener {
            DatePickerDialog(
                this, datePicker, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }


}
