package com.example.elitonpacheco.projetokitnet

import java.text.DecimalFormat


class ContasReceber {

    var id_conta: Long = 0
    var inquilino_conta: String = ""
    var valor_conta: Double =  0.0
    var status_conta: String = ""
    var data_conta: String = ""


    constructor(inquilino_conta:String,valor_conta:Double,status_conta:String,data_conta:String){

        this.inquilino_conta = inquilino_conta
        this.valor_conta= valor_conta
        this.status_conta = status_conta
        this.data_conta = data_conta
    }

    constructor(id_conta:Long,inquilino_conta:String,valor_conta:Double,status_conta:String,data_conta:String){

        this.id_conta = id_conta
        this.inquilino_conta = inquilino_conta
        this.valor_conta= valor_conta
        this.status_conta = status_conta
        this.data_conta = data_conta
    }

    constructor(id_conta:Long,inquilino_conta:String,data_conta:String,valor_conta:Double){
        this.id_conta = id_conta
        this.inquilino_conta = inquilino_conta
        this.data_conta = data_conta
        this.valor_conta= valor_conta
    }
    constructor(){

    }
}