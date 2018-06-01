package com.example.elitonpacheco.projetokitnet

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import kotlinx.android.synthetic.main.activity_main_cad_kitnet.*





class MainActivityCadKitnet : AppCompatActivity() {

    private lateinit var spstatus : Spinner
    private lateinit var splstinquilino:Spinner
    var lista_status = arrayOf("LIVRE","OCUPADO","RESERVADO")
    var resultado =  ArrayList<Get_Inquilinos>()
    var resultadotexto = ArrayList<String>()
    var dbinquilino  = DataBaseHandler(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_cad_kitnet)

        resultado = generateData()
        spstatus = findViewById(R.id.spinnerStatusKitnet)
        splstinquilino = findViewById<Spinner>(R.id.splstinquilinokitnet)
        resultadotexto.add("LIVRE")
        for(i in 0..(resultado.size -1)){
            resultadotexto.add(resultado[i].nome_inquilino)
        }


        val adapterstatus = ArrayAdapter(this,android.R.layout.simple_spinner_item,lista_status)
        val adapterinquilino = ArrayAdapter(this,android.R.layout.simple_spinner_item,resultadotexto)
        adapterstatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterinquilino.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spstatus.adapter = adapterstatus
        splstinquilino.adapter = adapterinquilino

        }

    private fun generateData(): ArrayList<Get_Inquilinos> {
        var resultadokitnet = ArrayList<Get_Inquilinos>()
        var dadoskitnet = dbinquilino.consultaInquilinos()

        for(i in 0..(dadoskitnet.size-1)){
            var apkit = Get_Inquilinos(dadoskitnet.get(i).nome_inquilino)

            resultadokitnet.add(apkit)
        }
        return resultadokitnet
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cad_kitnet, menu)
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        var id = item!!.itemId
        val context = this
        var errostatus = 0




        if (id == R.id.btn_cadastra_kitnet){
            if(txtNomeKitnet.text.toString().length == 0 || txtqtdQuartos.text.toString().length == 0 || txtNkitnet.text.toString().length == 0 || spstatus.selectedItem.toString().length == 0) {
                Toast.makeText(context, "Todos os Campos São Obrigatórios!!", Toast.LENGTH_LONG).show()
            }else{
                errostatus = 1
            }


            if(errostatus == 1) {
                var cad = cad_kitnet(txtNomeKitnet.text.toString(), txtqtdQuartos.text.toString(), txtNkitnet.text.toString(),spstatus.selectedItem.toString(),splstinquilino.selectedItem.toString())
                var db = DataBaseHandlerKitnet(context)
                db.inserirDados(cad)
                txtNomeKitnet.setText("")
                txtqtdQuartos.setText("")
                txtNkitnet.setText("")
                spinnerStatusKitnet.setSelection(0)
                splstinquilino.setSelection(0)
                txtNomeKitnet.requestFocus()
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}