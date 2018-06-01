package com.example.elitonpacheco.projetokitnet
class ListaInquilinos {
    var id_inquilino: String = ""
    var nome_inquilino: String = ""
    var email_inquilino: String = ""
    var cpf_inquilino: String = ""
    var celular_inquilino: String = ""
    var tipo_inquilino: String = ""

    constructor() {}

    constructor(id_inquilino:String, nome_inquilino: String, email_inquilino: String, cpf_inquilino: String, celular_inquilino: String, tipo_inquilino: String) {

        this.id_inquilino = id_inquilino
        this.nome_inquilino = nome_inquilino
        this.email_inquilino = email_inquilino
        this.cpf_inquilino = cpf_inquilino
        this.celular_inquilino = celular_inquilino
        this.tipo_inquilino = tipo_inquilino
    }
}