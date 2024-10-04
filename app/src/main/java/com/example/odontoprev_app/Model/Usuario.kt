package com.example.odontoprev_app.Model

import java.io.Serializable

class Usuario(
    val nome: String,
    val email: String,
    val dataNascimento: String,
    val cpf: String,
    val telefone: String,
    val rua: String,
    val bairro: String,
    val cep: String,
    val numero: Int
):Serializable {
}