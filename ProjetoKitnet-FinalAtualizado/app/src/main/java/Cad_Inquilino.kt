package com.example.elitonpacheco.projetokitnet



class Cad_Inquilino{

    var id_inquilino: Long = 0
    var nome_inquilino: String = ""
    var email_inquilino: String = ""
    var cpf_inquilino:String = ""
    var celular_inquilino: String = ""
    var senha_inquilino: String = ""
    var status_inquilino: String = ""
    var tipo_inquilino : String = ""

    constructor(nome_inquilino:String,email_inquilino:String,cpf_inquilino:String,celular_inquilino:String,senha_inquilino:String,status_inquilino:String,tipo_inquilino:String){

        this.nome_inquilino = nome_inquilino
        this.email_inquilino = email_inquilino
        this.cpf_inquilino = cpf_inquilino
        this.celular_inquilino = celular_inquilino
        this.senha_inquilino = senha_inquilino
        this.status_inquilino = status_inquilino
        this.tipo_inquilino = tipo_inquilino



    }

    constructor(){

    }

}