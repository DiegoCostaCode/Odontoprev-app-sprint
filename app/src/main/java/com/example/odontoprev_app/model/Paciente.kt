package com.example.odontoprev_app.model

import java.io.Serializable

class Paciente{
    var id = 0L;
    var nome = "";
    var email = "";
    var dataNascimento = "";
    var cpf = "";
    var telefone = ""


    override fun toString(): String {
        return "Paciente(id=$id, nome='$nome', email='$email', dataNascimento='$dataNascimento', cpf='$cpf', telefone='$telefone')"
    }


}



