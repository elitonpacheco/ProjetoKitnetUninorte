package com.example.elitonpacheco.projetokitnet


import android.support.v7.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login_projeto_kitnet.*
import android.widget.Toast



class LoginProjetoKitnet : AppCompatActivity() {

    var resultado =  ArrayList<Get_Inquilinos>()
    var resultadotexto = ArrayList<String>()
    var dbinquilino  = DataBaseHandler(this)
    val master = "masteradmin"
    val smaster = "adminprokitnet"
    var validacpf = ""
    var validaemail = ""
    var validasenha = ""
    var tipoinquilino = ""
    var nomeinquilino = ""
    var statusmaster = ""
    var contador = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_projeto_kitnet)


        resultado = generateData()

        btnentrar.setOnClickListener {
            var senha: String = senha_login.text.toString()
            var emailcpf: String = email_cpf.text.toString()

            if(resultado.size == 0 && emailcpf == master || resultado.size > 0 && emailcpf == master){
                statusmaster = "LOGANDO"
                if(emailcpf == master && senha == smaster ){
                        statusmaster = "LOGADO"
                        val telainicial = Intent(this, MainActivity_Kitnet::class.java)
                        startActivity(telainicial)
                        Toast.makeText(this, "Bem Vindo  Usuario Master", Toast.LENGTH_LONG).show()
                        finish()

                }else{
                    if(senha != smaster){
                        senha_login.setError("Senha Incorreta")
                    }

                }
            }else {

                    for (i in 0..(resultado.size - 1)) {

                        validacpf = resultado[i].cpf_inquilino
                        validaemail = resultado[i].email_inquilino
                        validasenha = resultado[i].senha_inquilino
                        tipoinquilino = resultado[i].tipo_inquilino
                        nomeinquilino = resultado[i].nome_inquilino
                        contador +=1

                            if (emailcpf == validacpf || emailcpf == validaemail) {
                                if(senha != validasenha) {
                                    senha_login.setError("Senha Incorreta")
                                }else {
                                    val telainicial = Intent(this, MainActivity_Kitnet::class.java)
                                    telainicial.putExtra("nomeinquilino", resultado[i].nome_inquilino)
                                    telainicial.putExtra("tipoinquilino", resultado[i].tipo_inquilino)
                                    startActivity(telainicial)
                                    Toast.makeText(this, "Bem Vindo " + nomeinquilino, Toast.LENGTH_LONG).show()
                                    finish()
                                    break
                                }
                                }

                        if(contador == resultado.size && emailcpf != validaemail && emailcpf != validacpf ) {
                            Toast.makeText(this, "Inquilino não Cadastrado Entre em contato com Administrador", Toast.LENGTH_LONG).show()
                            email_cpf.setError("Email ou CPF Invalido")
                        }

                            }
                        if(contador == 0 ){
                            Toast.makeText(this, "Inquilino não Cadastrado Entre em contato com Administrador", Toast.LENGTH_LONG).show()
                                email_cpf.setError("Email ou CPF Invalido")
                        }

                    }
                contador = 0

                }


    }



    private fun generateData(): ArrayList<Get_Inquilinos> {
        var resultadoinquilino = ArrayList<Get_Inquilinos>()
        var dadosinquilino = dbinquilino.ValidaLogin()

        for(i in 0..(dadosinquilino.size-1)){
            var apkit = Get_Inquilinos(dadosinquilino.get(i).id_inquilino,
                    dadosinquilino.get(i).nome_inquilino,
                    dadosinquilino.get(i).cpf_inquilino,
                    dadosinquilino.get(i).senha_inquilino,
                    dadosinquilino.get(i).email_inquilino,
                    dadosinquilino.get(i).tipo_inquilino)

            resultadoinquilino.add(apkit)
        }
        return resultadoinquilino
    }


}




