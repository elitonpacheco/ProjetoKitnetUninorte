package com.example.elitonpacheco.projetokitnet

class ListaKitnet {
    var id_kitnet: String = ""
    var nome_kitnet: String = ""
    var qtd_quartos: String = ""
    var num_kitnet: String = ""
    var status_kitnet: String = ""
    var inquilino_kitnet: String = ""

    constructor(){}

    constructor(id_kitnet:String,nome_kitnet:String,qtd_quartos:String,num_kitnet:String,status_kitnet:String,inquilino_kitnet:String){

        this.id_kitnet = id_kitnet
        this.nome_kitnet = nome_kitnet
        this.qtd_quartos = qtd_quartos
        this.num_kitnet = num_kitnet
        this.status_kitnet = status_kitnet
        this.inquilino_kitnet = inquilino_kitnet

    }

}