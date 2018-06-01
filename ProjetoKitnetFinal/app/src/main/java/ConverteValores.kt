package com.example.elitonpacheco.projetokitnet


class ConverteValores {



    fun TextoParaValor(valorcomMascara:String):Double{

        val valor = valorcomMascara.replace(".","").replace("R$","").replace(",",".")
         val valorfinal =valor.toDouble()

        return valorfinal
    }





}