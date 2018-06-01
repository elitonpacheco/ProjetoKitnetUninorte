package com.example.elitonpacheco.projetokitnet


class Get_Inquilinos {


    var id_inquilino: Long = 0
    var nome_inquilino: String = ""
    var cpf_inquilino: String = ""
    var senha_inquilino: String = ""
    var email_inquilino: String = ""
    var tipo_inquilino: String = ""

    constructor(id_inquilino:Long,nome_inquilino:String,cpf_inquilino:String,senha_inquilino:String,email_inquilino:String,tipo_inquilino:String){

        this.id_inquilino = id_inquilino
        this.nome_inquilino = nome_inquilino
        this.cpf_inquilino = cpf_inquilino
        this.email_inquilino = email_inquilino
        this.senha_inquilino = senha_inquilino
        this.tipo_inquilino = tipo_inquilino

    }



    constructor(nome_inquilino:String){

        this.nome_inquilino = nome_inquilino

    }

    constructor(){

    }
}