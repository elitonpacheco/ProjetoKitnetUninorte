package com.example.elitonpacheco.projetokitnet

class cad_kitnet {

    var id_kitnet: Long = 0
    var nome_kitnet: String = ""
    var qtd_quartos: String = ""
    var num_kitnet: String = ""
    var status_kitnet: String = ""
    var inquilino_kitnet: String = ""

    constructor(nome_kitnet:String,qtd_quartos:String,num_kitnet:String,status_kitnet:String,inquilino_kitnet:String){

        this.nome_kitnet = nome_kitnet
        this.qtd_quartos = qtd_quartos
        this.num_kitnet = num_kitnet
        this.status_kitnet = status_kitnet
        this.inquilino_kitnet = inquilino_kitnet

    }

    constructor(){

    }
}