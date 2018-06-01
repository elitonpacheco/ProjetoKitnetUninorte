package com.example.elitonpacheco.projetokitnet

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.content.RestrictionsManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main_recibo.*
import android.widget.EditText
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main_cad_kitnet.*
import java.lang.ref.WeakReference
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*


class MainActivityRecibo : AppCompatActivity() {
    private lateinit var splstinquilino: Spinner
    var resultado =  ArrayList<Get_Inquilinos>()
    var resultadotexto = ArrayList<String>()
    var dbinquilino  = DataBaseHandler(this)
    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main_recibo)
            val mLocale = Locale("pt", "BR")
            txtvalorecibo.addTextChangedListener(MaskMoeda.MoneyTextWatcher(txtvalorecibo,mLocale))
        resultado = generateData()
        splstinquilino = findViewById<Spinner>(R.id.spinquilinoconta)
        for(i in 0..(resultado.size -1)){
            resultadotexto.add(resultado[i].nome_inquilino)
        }
        val adapterinquilino = ArrayAdapter(this,android.R.layout.simple_spinner_item,resultadotexto)
        adapterinquilino.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
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

                        @SuppressLint("SetTextI18n")
                        fun funDate (view: View){
                                    val c = Calendar.getInstance()
                                    val day = c.get(Calendar.DAY_OF_MONTH)
                                    val month = c.get(Calendar.MONTH)
                                    val year = c.get(Calendar.YEAR)

                                    val dpd = DatePickerDialog(this, android.R.style.Theme_DeviceDefault_Dialog_Alert, DatePickerDialog.OnDateSetListener { datePicker, year, monthOfYear, dayOfMonth ->
                                        var corrigemes = monthOfYear + 1
                                        txtdataconta.text ="$dayOfMonth/$corrigemes/$year"
                                    }, year, month , day)

                                    //show datepicker
                                    dpd.show()
                        }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_contas, menu)
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        var id = item!!.itemId
        val context = this
        var errostatus = 0
        var statusvalor = ""



        if (id == R.id.btnsalvaconta) {
            if (txtdataconta.text == "" || txtvalorecibo.text.toString() == "" || rbpago.isChecked == false && rbpendente.isChecked == false) {
                Toast.makeText(context, "Todos os Campos São Obrigatórios!!", Toast.LENGTH_LONG).show()
            } else {
                errostatus = 1
            }
            if (rbpago.isChecked == true) {
                statusvalor = "PAGO"
            }
            if (rbpendente.isChecked == true) {
                statusvalor = "PENDENTE"
            }

            if (spinquilinoconta.selectedItem == null) {
                Toast.makeText(this, "Favor Cadastra um Inquilino para Usar Esta Opção", Toast.LENGTH_LONG).show()
                finish()
            } else {

                val valorconta = ConverteValores().TextoParaValor(txtvalorecibo.text.toString())
                val dataconta = txtdataconta.text

                if (errostatus == 1) {
                    var cad = ContasReceber(spinquilinoconta.selectedItem.toString(), valorconta, statusvalor, dataconta.toString())
                    var db = DbContasReceber(context)
                    db.inserirDados(cad)
                    finish()
                    val telagerarecibo = Intent(this, MainActivityRecibo::class.java)
                    startActivity(telagerarecibo)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }



}





