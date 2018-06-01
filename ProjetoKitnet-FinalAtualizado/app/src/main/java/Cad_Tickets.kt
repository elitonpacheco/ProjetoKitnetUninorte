package com.example.elitonpacheco.projetokitnet



class Cad_Tickets {

    var id_ticket: Long = 0
    var desc_ticket: String = ""
    var status_ticket: String = ""
    var inquilino_ticket: String = ""
    var data_ticket: String = ""



    constructor(id_ticket:Long,desc_ticket:String,status_ticket:String,data_ticket:String,inquilino_ticket:String){
        this.id_ticket = id_ticket
        this.desc_ticket = desc_ticket
        this.status_ticket = status_ticket
        this.data_ticket = data_ticket
        this.inquilino_ticket = inquilino_ticket
    }

    constructor(desc_ticket:String,status_ticket: String,data_ticket:String,inquilino_ticket:String){
        this.desc_ticket = desc_ticket
        this.status_ticket = status_ticket
        this.data_ticket = data_ticket
        this.inquilino_ticket = inquilino_ticket
    }

    constructor(id_ticket: Long){
        this.id_ticket = id_ticket
    }

    constructor(){

    }
}

