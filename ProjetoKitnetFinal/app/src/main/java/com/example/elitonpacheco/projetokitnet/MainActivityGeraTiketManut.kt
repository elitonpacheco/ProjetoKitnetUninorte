package com.example.elitonpacheco.projetokitnet

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.content.pm.PackageManager
import android.app.Activity
import android.support.v4.app.ActivityCompat
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main_gera_tiket_manut.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivityGeraTiketManut : AppCompatActivity() {
    private lateinit var splstinquilino: Spinner
    val CAMERA_REQUEST_CODE = 0
    var resultado =  ArrayList<Cad_Tickets>()
    var resultadoinqui = ArrayList<Get_Inquilinos>()
    var resultadotexto = ArrayList<String>()
    var contador = 1
    var banco = DbTicketsKitnet(this)
    var dbinquilino = DataBaseHandler(this)
    var numeroid = 0
    var data =  getDateTime()
    var dataatual = data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_gera_tiket_manut)
        var numeroticket = findViewById<TextView>(R.id.txtNumTiket)
        txtdataticket.setText("Data: " + data)
        getPermissions()
        resultado = generateDataIdTicket()
        resultadoinqui = generateDataInquilino()
        numeroid = resultado.size + 1
        numeroticket.text = "Ticket Nº: " + numeroid
        splstinquilino = findViewById<Spinner>(R.id.spinquilinoticket)
        for(i in 0..(resultadoinqui.size -1)){
            resultadotexto.add(resultadoinqui[i].nome_inquilino)
        }

        val adapterinquilino = ArrayAdapter(this,android.R.layout.simple_spinner_item,resultadotexto)
        adapterinquilino.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        splstinquilino.adapter = adapterinquilino

        btnCapFoto.setOnClickListener {
            val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (callCameraIntent.resolveActivity(packageManager) != null) {
                startActivityForResult(callCameraIntent, CAMERA_REQUEST_CODE)
            }

        }


    }

    private fun generateDataIdTicket(): ArrayList<Cad_Tickets> {
        var resultadoticket = ArrayList<Cad_Tickets>()
        var dadosticket = banco.consultaId()

        for(i in 0..(dadosticket.size-1)){
            var aptik = Cad_Tickets(dadosticket.get(i).id_ticket)

            resultadoticket.add(aptik)
        }
        return resultadoticket
    }

    private fun generateDataInquilino(): ArrayList<Get_Inquilinos> {
        var resultadoinquilino = ArrayList<Get_Inquilinos>()
        var dadosinquilino = dbinquilino.consultaInquilinos()

        for(i in 0..(dadosinquilino.size-1)){
            var apkit = Get_Inquilinos(dadosinquilino.get(i).nome_inquilino)

            resultadoinquilino.add(apkit)
        }
        return resultadoinquilino
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                   when(contador){
                       1->{
                           imgVisualiza01.setImageBitmap(data.extras.get("data") as Bitmap)
                           contador+=1
                       }
                       2->{
                           imgVisualiza02.setImageBitmap(data.extras.get("data") as Bitmap)
                           contador+=1
                       }
                       3->{
                           imgVisualiza03.setImageBitmap(data.extras.get("data") as Bitmap)
                           contador+=1
                       }
                       4->{
                           imgVisualiza04.setImageBitmap(data.extras.get("data") as Bitmap)
                           contador+=1
                       }
                       5->{
                           imgVisualiza05.setImageBitmap(data.extras.get("data") as Bitmap)
                           contador+=1
                       }
                       else->{
                           Toast.makeText(this,"Numero Máximo do Fotos são 5", Toast.LENGTH_SHORT).show()
                       }


                    }

                }
            }
            else -> {
                Toast.makeText(this, "Falha na Imagem", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun getPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else
            dispatchTakePictureIntent()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    dispatchTakePictureIntent()
                } else {
                    Toast.makeText(this, "Não vai funcionar!!!", Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }

   private fun dispatchTakePictureIntent() {


        }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_ticket, menu)
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        var id = item!!.itemId
        val context = this
        var errostatus = 0




        if (id == R.id.btnsalvaticket){
            if(txtProblema.length() == 0) {
                Toast.makeText(context, "Por favor Informe a Descrição do Problema!!", Toast.LENGTH_LONG).show()
            }else{
                errostatus = 1
            }


            if(errostatus == 1) {
                var statustiket = "ABERTO"
                var cad = Cad_Tickets(txtProblema.text.toString(),statustiket,dataatual,splstinquilino.selectedItem.toString())
                var db = DbTicketsKitnet(context)
                db.inserirDados(cad)
                finish()
                val telageratiket = Intent(this, MainActivityGeraTiketManut::class.java)
                startActivity(telageratiket)

            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getDateTime(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val date = Date()
        return dateFormat.format(date)
    }

}



